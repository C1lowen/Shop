package com.aroma.shop.shop.dto;

import lombok.Data;

import java.util.List;
@Data
public class ResponseFavAndLikes {
    private Boolean auth;
    private List<Long> products;
    private List<Long> likes;

    public ResponseFavAndLikes(Boolean auth, List<Long> products, List<Long> likes) {
        this.auth = auth;
        this.products = products;
        this.likes = likes;
    }
}
