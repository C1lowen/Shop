package com.aroma.shop.shop.dto;

import lombok.Data;

@Data
public class PromoDTO {
    private Boolean active;
    private Boolean auth;
    private Integer discount;
    private String promo;
    private String reason;

    public PromoDTO(Boolean active, Boolean auth, Integer discount, String promo, String reason) {
        this.active = active;
        this.auth = auth;
        this.discount = discount;
        this.promo = promo;
        this.reason = reason;
    }
}
