package com.ecnu.hmg.hmggoodshopservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 这个类的作用，是接受插入请求传来的参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Good_Shop {
    //private Integer goodId;//这个是自动生成的
    private String type;
    private String name;
    private Double price;
    private String description;

    private Integer shopId;
}
