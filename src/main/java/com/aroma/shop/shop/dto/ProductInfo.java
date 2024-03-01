package com.aroma.shop.shop.dto;

import lombok.Data;

@Data
public class ProductInfo {
    private Long id;
    private Long count;
    private Long size;

    public ProductInfo(Long id, Long count, Long size) {
        this.id = id;
        this.count = count;
        this.size = size;
    }
}
