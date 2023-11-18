package com.redis.model;

import lombok.Data;

@Data
public class ProductInfoDTO {
    private String name;
    private String id;
    private int price;
    private int sum;
}
