package com.aroma.shop.shop.dto;

import lombok.Data;

@Data
public class OrderCodeDTO {
    private String name;
    private Integer count;
    private Integer price;
    private String pathImages;

    public OrderCodeDTO(String name, Integer count, Integer price, String pathImages) {
        this.name = name;
        this.count = count;
        this.price = price;
        this.pathImages = pathImages;
    }
}
