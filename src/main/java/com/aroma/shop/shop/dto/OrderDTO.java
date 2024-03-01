package com.aroma.shop.shop.dto;

import com.aroma.shop.shop.model.Products;
import com.aroma.shop.shop.model.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

import java.util.List;
@Data
public class OrderDTO {
    private Long id;
    private Integer price;
    private Integer discount;
    private List<ProductInfo> products;
    private String phone;
    private String delivery;
    private String deliveryPrice;

}
