package com.aroma.shop.shop.dto;

import lombok.Data;

@Data
public class ResponseOrder {
    private Long id;
    private Boolean isAuth;
    private Boolean deleteLocalProduct;
    private Boolean validResponse;

    public ResponseOrder() {
    }

    public ResponseOrder(Long id, Boolean isAuth, Boolean deleteLocalProduct, Boolean validResponse) {
        this.id = id;
        this.isAuth = isAuth;
        this.deleteLocalProduct = deleteLocalProduct;
        this.validResponse = validResponse;
    }
}
