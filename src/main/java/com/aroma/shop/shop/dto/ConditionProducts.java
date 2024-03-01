package com.aroma.shop.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConditionProducts {
    private String[] gender;
    private String[] color;
    private String[] brands;
    private Double priceStart;
    private Double priceEnd;
    private String sorted;

    public ConditionProducts(String[] gender, String[] color, String[] brands, Double priceStart, Double priceEnd, String sorted) {
        this.gender = gender;
        this.color = color;
        this.brands = brands;
        this.priceStart = priceStart;
        this.priceEnd = priceEnd;
        this.sorted = sorted;
    }
}
