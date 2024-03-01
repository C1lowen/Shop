package com.aroma.shop.shop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "promo_user")
@NoArgsConstructor
@Data
public class PromoUser {
    @Id
    @Column(name="id_user")
    private Long userId;
    @Column(name = "id_promo")
    private Long promoId;
}
