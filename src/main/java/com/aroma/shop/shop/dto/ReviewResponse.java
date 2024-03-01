package com.aroma.shop.shop.dto;

import com.aroma.shop.shop.model.Review;
import lombok.Data;

import java.util.List;

@Data
public class ReviewResponse {
    private boolean success;
    private List<Review> answer;
    private Integer size;
    private Double average;
    private StatisticsStar statistics;

    public ReviewResponse(boolean success, List<Review> answer, Integer size, Double average, StatisticsStar statistics) {
        this.success = success;
        this.answer = answer;
        this.size = size;
        this.average = average;
        this.statistics = statistics;
    }
}
