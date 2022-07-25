package com.liu.es.dto;

import lombok.Data;

/**
 * @author: miao.liu
 * @create: 2022-07-25 22:52
 * @describe:
 **/
@Data
public class RequestParams {

    private String key;
    private Integer page;
    private Integer size;
    private String sortBy;

    private String brand;
    private String city;
    private String starName;

    private Integer minPrice;
    private Integer maxPrice;

    private String location;
}
