package com.aroma.shop.shop.dto;

import lombok.Data;

@Data
public class PromoAuthDTO {
    private Boolean auth;
    private Boolean active;
    private Integer discount;
    private String reason;

    public PromoAuthDTO(Boolean auth, Boolean active, Integer discount, String reason) {
        this.auth = auth;
        this.active = active;
        this.discount = discount;
        this.reason = reason;
    }

    public PromoAuthDTO() {
    }
}
