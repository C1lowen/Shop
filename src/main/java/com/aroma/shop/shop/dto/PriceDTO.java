package com.aroma.shop.shop.dto;

import lombok.Data;

@Data
public class PriceDTO {
    private double minPrice;
    private double maxPrice;


    public PriceDTO(double minPrice, double maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }
}
