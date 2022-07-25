package com.liu.es.service.impl;

import com.alibaba.fastjson.JSON;
import com.liu.es.BaseApplicationTests;
import com.liu.es.constants.HotelConstants;
import com.liu.es.dao.Hotel;
import com.liu.es.doc.HotelDoc;
import com.liu.es.dto.RequestParams;
import com.liu.es.mapper.HotelMapper;
import com.liu.es.service.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * @author: miao.liu
 * @create: 2022-07-23 14:55
 * @describe:
 **/
@Slf4j
class HotelServiceImplTest extends BaseApplicationTests {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private HotelMapper hotelMapper;


    @Test
    void testCreateHotelIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("hotel");

        request.source(HotelConstants.MAPPING_TEMPLATE, XContentType.JSON);

        restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
    }

    @Test
    void testDeleteHotelIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("hotel");
        restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
    }

    @Test
    void testExistsHotelIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("hotel");
        boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        log.info("是否存在：{}", exists);
    }

    @Test
    void addDocument() throws IOException {

        Hotel hotel = hotelMapper.selectById(61083L);

        HotelDoc hotelDoc = new HotelDoc(hotel);

        IndexRequest request = new IndexRequest("hotel").id(hotel.getId().toString());

        request.source(JSON.toJSONString(hotelDoc), XContentType.JSON);

        restHighLevelClient.index(request, RequestOptions.DEFAULT);
    }

    @Test
    void bulkDocument() throws IOException {

        List<Hotel> list = hotelService.list();

        BulkRequest request = new BulkRequest();
        for (Hotel hotel : list) {
            HotelDoc hotelDoc = new HotelDoc(hotel);
            IndexRequest indexReq = new IndexRequest("hotel")
                    .id(hotel.getId().toString())
                    .source(JSON.toJSONString(hotelDoc), XContentType.JSON);
            request.add(indexReq);
        }
        restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
    }

    @Test
    void getDocumentById() throws IOException {

        GetRequest request = new GetRequest("hotel").id("61083");
        GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        response.getSourceAsString();
        HotelDoc hotelDoc = JSON.parseObject(response.getSourceAsString(), HotelDoc.class);
        log.info("hotelDoc:{}", hotelDoc);
    }

    @Test
    void updateDocumentById() throws IOException {

        UpdateRequest request = new UpdateRequest("hotel", "61083");
        request.doc(
                "price", "952"
        );
        restHighLevelClient.update(request, RequestOptions.DEFAULT);
    }

    @Test
    void deleteDocumentById() throws IOException {

        DeleteRequest request = new DeleteRequest("hotel", "61083");

        restHighLevelClient.delete(request, RequestOptions.DEFAULT);
    }

    @Test
    void testMatchAll() throws IOException {


        SearchRequest request = new SearchRequest("hotel");
        request.source()
                .query(QueryBuilders.matchAllQuery());
        SearchResponse search = restHighLevelClient.search(request, RequestOptions.DEFAULT);

        extracted(search);

    }

    @Test
    void testMatch() throws IOException {


        SearchRequest request = new SearchRequest("hotel");
        request.source()
                .query(QueryBuilders.matchQuery("all", "如家"));
        SearchResponse search = restHighLevelClient.search(request, RequestOptions.DEFAULT);

        extracted(search);

    }

    @Test
    void testBool() throws IOException {


        SearchRequest request = new SearchRequest("hotel");

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.termQuery("city", "上海"));
        boolQuery.filter(QueryBuilders.rangeQuery("price").lte("250"));
        request.source().query(boolQuery);
        SearchResponse search = restHighLevelClient.search(request, RequestOptions.DEFAULT);

        extracted(search);

    }

    @Test
    void testPageAndSort() throws IOException {

        int page = 4, size = 5;

        SearchRequest request = new SearchRequest("hotel");
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.matchQuery("all", "如家"));
        boolQuery.filter(QueryBuilders.rangeQuery("price").lte("250"));
        request.source().query(boolQuery);

        request.source().highlighter(new HighlightBuilder().field("name").requireFieldMatch(false));

        request.source().sort("price", SortOrder.ASC);
        request.source().from((page - 1) * size).size(size);
        SearchResponse search = restHighLevelClient.search(request, RequestOptions.DEFAULT);

        extracted(search);

    }

    @Test
    void searchPage() throws IOException {

        RequestParams params = new RequestParams();
        params.setPage(1);
        params.setSize(10);
        params.setKey("如家");
        params.setMinPrice(0);
        params.setMaxPrice(250);
        params.setLocation("22.602482,114.123284");

        hotelService.search(params);

    }

    private void extracted(SearchResponse search) {
        SearchHits searchHits = search.getHits();
        long value = searchHits.getTotalHits().value;
        log.info("total size :{}", value);
        SearchHit[] hits = searchHits.getHits();

        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();

            HotelDoc hotelDoc = JSON.parseObject(sourceAsString, HotelDoc.class);

            Map<String, HighlightField> highlightFields = hit.getHighlightFields();

            if (!CollectionUtils.isEmpty(highlightFields)) {
                HighlightField name = highlightFields.get("name");
                if (name != null) {
                    String fragment = name.getFragments()[0].toString();
                    hotelDoc.setName(fragment);
                }
            }
            log.info("hotelDoc:{}", hotelDoc);
        }
    }

    @BeforeEach
    void setUP() {
        log.info("start time -- {}", System.currentTimeMillis());
    }

    @AfterEach
    void testDown() {
        log.info("end   time -- {}", System.currentTimeMillis());
    }
}