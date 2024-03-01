package com.aroma.shop.shop.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="order_product_info")
@Data
@NoArgsConstructor
public class OrderProductInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product")
    private Products products;
    private Long count;
    private Long size;

    public OrderProductInfo(Products products, Long count, Long size) {
        this.products = products;
        this.count = count;
        this.size = size;
    }
}
