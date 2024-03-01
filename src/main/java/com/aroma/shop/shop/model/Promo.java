package com.aroma.shop.shop.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity(name="promo")
@Data
@NoArgsConstructor
public class Promo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "promo_name")
    private String promo;
    @Column(name = "discount")
    private Integer discount;
    @Column(name = "count_activation")
    private Integer countActivation;
    @Column(name = "active_date")
    private LocalDate activateDate;
}
