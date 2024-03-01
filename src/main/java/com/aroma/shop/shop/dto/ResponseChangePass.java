package com.aroma.shop.shop.dto;

import lombok.Data;

@Data
public class ResponseChangePass {
    private Boolean change;
    private String answer;

    public ResponseChangePass(Boolean change, String answer) {
        this.change = change;
        this.answer = answer;
    }
}
