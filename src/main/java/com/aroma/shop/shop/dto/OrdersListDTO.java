package com.aroma.shop.shop.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrdersListDTO {
    private Long id;
    private List<ProductOrdersInfo> products;
    private Integer subtotal;
    private Integer discount;

    public OrdersListDTO(Long id, List<ProductOrdersInfo> products, Integer subtotal, Integer discount) {
        this.id = id;
        this.products = products;
        this.subtotal = subtotal;
        this.discount = discount;
    }
}
