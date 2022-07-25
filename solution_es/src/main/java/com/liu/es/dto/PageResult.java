package com.liu.es.dto;

import com.liu.es.doc.HotelDoc;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: miao.liu
 * @create: 2022-07-25 22:52
 * @describe:
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult {

    private Long total;
    private List<HotelDoc> hotels;
}
