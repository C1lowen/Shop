package com.aroma.shop.shop.dto;

import lombok.Data;

@Data
public class SpecificationsProduct {
    private Long productId;
    private Long size;
    private Long count;

    public SpecificationsProduct() {
    }

    public SpecificationsProduct(Long productId, Long size, Long count) {
        this.productId = productId;
        this.size = size;
        this.count = count;
    }

}
