package com.aroma.shop.shop.dto;

import com.aroma.shop.shop.model.Products;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import lombok.Data;

@Data
public class FavoriteDTO {
    private Long id;
    private Products product;
    private Long size;
    private Long count;

    public FavoriteDTO(Long id, Products product, Long size, Long count) {
        this.id = id;
        this.product = product;
        this.size = size;
        this.count = count;
    }
}
