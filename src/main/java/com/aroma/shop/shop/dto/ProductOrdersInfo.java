package com.aroma.shop.shop.dto;

import lombok.Data;

@Data
public class ProductOrdersInfo {
    private String name;
    private String images;
    private Long size;
    private Long count;
    private Double price;

    public ProductOrdersInfo(String name, String images, Long size, Long count, Double price) {
        this.name = name;
        this.images = images;
        this.size = size;
        this.count = count;
        this.price = price;
    }
}
