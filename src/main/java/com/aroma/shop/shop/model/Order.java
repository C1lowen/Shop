package com.aroma.shop.shop.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name="order_cart")
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Integer price;
    private Integer discount;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "order_cart_products",
            joinColumns = @JoinColumn(name = "order_cart_id"),
            inverseJoinColumns = @JoinColumn(name = "order_product_info_id")
    )
    private List<OrderProductInfo> products;
    private String phone;
    private String delivery;
    private String deliveryPrice;

    public Order(Long userId, Integer price, Integer discount, List<OrderProductInfo> products, String phone, String delivery, String deliveryPrice) {
        this.userId = userId;
        this.price = price;
        this.discount = discount;
        this.products = products;
        this.phone = phone;
        this.delivery = delivery;
        this.deliveryPrice = deliveryPrice;
    }
}
