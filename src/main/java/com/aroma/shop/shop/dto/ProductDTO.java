package com.aroma.shop.shop.dto;

import com.aroma.shop.shop.model.Specification;
import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String images;
    private String category;
    private String gender;
    private String color;
    private Double price;
    private Specification specification;

    public ProductDTO(Long id, String name, String images, String category, String gender, String color, Double price, Specification specification) {
        this.id = id;
        this.name = name;
        this.images = images;
        this.category = category;
        this.gender = gender;
        this.color = color;
        this.price = price;
        this.specification = specification;
    }

}
