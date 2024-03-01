package com.aroma.shop.shop.dto;

import com.aroma.shop.shop.model.Favorite;
import com.aroma.shop.shop.model.Products;
import lombok.Data;

import java.util.List;
@Data
public class AuthCartProduct {
    private Boolean auth;
    private List<FavoriteDTO> products;

    public AuthCartProduct(Boolean auth, List<FavoriteDTO> products) {
        this.auth = auth;
        this.products = products;
    }
}
