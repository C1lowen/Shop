package com.aroma.shop.shop.dto;

import lombok.Data;

@Data
public class CountPage {
    private Boolean auth;
    private Integer countPage;
    private Integer countLikes;

    public CountPage(Boolean auth, Integer countPage, Integer countLikes) {
        this.auth = auth;
        this.countPage = countPage;
        this.countLikes = countLikes;
    }
}
