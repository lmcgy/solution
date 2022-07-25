package com.liu.es.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.es.dao.Hotel;
import com.liu.es.dto.PageResult;
import com.liu.es.dto.RequestParams;

/**
 * @author: miao.liu
 * @create: 2022-07-23 13:41
 * @describe:
 **/
public interface HotelService extends IService<Hotel> {

    PageResult search(RequestParams query);
}
