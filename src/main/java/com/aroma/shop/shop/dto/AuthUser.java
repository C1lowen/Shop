package com.aroma.shop.shop.dto;

import lombok.Data;

@Data
public class AuthUser {
    private Boolean auth;
    private Boolean findProduct;

    public AuthUser(Boolean auth, Boolean findProduct) {
        this.auth = auth;
        this.findProduct = findProduct;
    }
}
