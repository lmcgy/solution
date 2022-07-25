package com.liu.es.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.es.dao.Hotel;
import com.liu.es.doc.HotelDoc;
import com.liu.es.dto.PageResult;
import com.liu.es.dto.RequestParams;
import com.liu.es.mapper.HotelMapper;
import com.liu.es.service.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: miao.liu
 * @create: 2022-07-23 13:41
 * @describe:
 **/
@Service
@Slf4j
public class HotelServiceImpl extends ServiceImpl<HotelMapper, Hotel> implements HotelService {


    @Resource
    private RestHighLevelClient client;


    @Override
    public PageResult search(RequestParams query) {
        try {
            SearchRequest request = new SearchRequest("hotel");

            BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

            if (StringUtils.isEmpty(query.getKey())) {
                boolQuery.must(QueryBuilders.matchAllQuery());
            } else {
                boolQuery.must(QueryBuilders.matchQuery("all", query.getKey()));
            }
            // 城市
            if (!StringUtils.isEmpty(query.getCity())){
                boolQuery.filter(QueryBuilders.termQuery("city",query.getCity()));
            }
            // 品牌
            if (!StringUtils.isEmpty(query.getBrand())){
                boolQuery.filter(QueryBuilders.termQuery("brand",query.getBrand()));
            }
            // 星级
            if (!StringUtils.isEmpty(query.getStarName())){
                boolQuery.filter(QueryBuilders.termQuery("startName",query.getStarName()));
            }
            // 价格
            if (query.getMinPrice() != null && query.getMaxPrice() != null){
                boolQuery.filter(QueryBuilders.rangeQuery("price").gte(query.getMinPrice()).lte(query.getMaxPrice()));
            }

            // 算分控制
            FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(
                    boolQuery,
                    new FunctionScoreQueryBuilder.FilterFunctionBuilder[]{
                            new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                                    // 过滤条件
                                    QueryBuilders.termQuery("isAD",true),
                                    // 算分函数
                                    ScoreFunctionBuilders.weightFactorFunction(10)
                            )
                    });
            request.source().query(functionScoreQueryBuilder);

            request.source().sort("price", SortOrder.ASC);
            if (!StringUtils.isEmpty(query.getLocation())){
                request.source().sort(SortBuilders
                        .geoDistanceSort("location",new GeoPoint(query.getLocation()))
                        .order(SortOrder.ASC)
                        .unit(DistanceUnit.KILOMETERS));
            }

            request.source().from((query.getPage() - 1) * query.getSize()).size(query.getSize());

            SearchResponse search = client.search(request, RequestOptions.DEFAULT);

            return handleResponse(search);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private PageResult handleResponse(SearchResponse search) {
        SearchHits searchHits = search.getHits();
        long value = searchHits.getTotalHits().value;
        log.info("total size :{}", value);
        SearchHit[] hits = searchHits.getHits();

        List<HotelDoc> hotelDocs = new ArrayList<>();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();

            HotelDoc hotelDoc = JSON.parseObject(sourceAsString, HotelDoc.class);

            // 距离
             Object[] sortValues = hit.getSortValues();
             if (sortValues != null && sortValues.length > 0){
                 hotelDoc.setDistance(sortValues[0]);
             }

             // 高亮
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();

            if (!CollectionUtils.isEmpty(highlightFields)) {
                HighlightField name = highlightFields.get("name");
                if (name != null) {
                    String fragment = name.getFragments()[0].toString();
                    hotelDoc.setName(fragment);
                }
            }

            hotelDocs.add(hotelDoc);
            log.info("hotelDoc:{}", hotelDoc);
        }

        return new PageResult(value, hotelDocs);
    }
}
