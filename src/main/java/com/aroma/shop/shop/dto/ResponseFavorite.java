package com.aroma.shop.shop.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseFavorite {
    private Boolean auth;
    private List<SpecificationsProduct> products;

    public ResponseFavorite() {
    }

    public ResponseFavorite(Boolean auth, List<SpecificationsProduct> products) {
        this.auth = auth;
        this.products = products;
    }
}
