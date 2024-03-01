package com.aroma.shop.shop.dto;

import lombok.Data;

@Data
public class ProductShortDTO {

    private Long id;
    private String name;
    private String images;
    private Double price;

    public ProductShortDTO(Long id, String name, String images, Double price) {
        this.id = id;
        this.name = name;
        this.images = images;
        this.price = price;
    }
}
