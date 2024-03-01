package com.aroma.shop.shop.controller;

import com.aroma.shop.shop.dto.ReviewResponse;
import com.aroma.shop.shop.model.Review;
import com.aroma.shop.shop.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/review")
    @ResponseBody
    public ResponseEntity<ReviewResponse> review(@RequestBody Review review){
        if(review == null) {
            return ResponseEntity.ok(new ReviewResponse(false, null, null, null, null));
        }
        reviewService.save(review);
        Long id = review.getIdProduct();
        List<Review> reviewByIdPost = reviewService.findByIdProduct(id);
        return ResponseEntity.ok(new ReviewResponse(true, reviewByIdPost, reviewService.findByIdProductAll(id).size(),
                reviewService.findCountIdProduct(id), reviewService.findStarsCount(id)));
    }

    @PostMapping("/fullreview")
    @ResponseBody
    public List<Review> review(@RequestBody Map<String, Object> requestData) {
        Long id = ((Number) requestData.get("id")).longValue();
        return reviewService.findByIdProductStart(id);
    }

    @PostMapping("/fistreview")
    @ResponseBody
    public List<Review> reviewFirst(@RequestBody Map<String, Object> requestData) {
        Long id = ((Number) requestData.get("id")).longValue();
        return reviewService.findByIdProduct(id);
    }
}
