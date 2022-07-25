package com.liu.es.controller;

import com.liu.es.dto.PageResult;
import com.liu.es.dto.RequestParams;
import com.liu.es.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: miao.liu
 * @create: 2022-07-23 13:42
 * @describe:
 **/
@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping("/list")
    public PageResult search(@RequestBody RequestParams query){
        return hotelService.search(query);
    }
}
