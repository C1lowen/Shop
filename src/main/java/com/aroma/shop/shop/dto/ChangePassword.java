package com.aroma.shop.shop.dto;

import lombok.Data;

@Data
public class ChangePassword {
    private String originalCode;
    private String passOrig;
    private String passRepeat;
}
