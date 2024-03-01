package com.aroma.shop.shop.dto;

import lombok.Data;

@Data
public class StatisticsStar {
    private Integer five;
    private Integer four;
    private Integer three;
    private Integer two;
    private Integer one;

    public StatisticsStar(Integer five, Integer four, Integer three, Integer two, Integer one) {
        this.five = five;
        this.four = four;
        this.three = three;
        this.two = two;
        this.one = one;
    }
}
