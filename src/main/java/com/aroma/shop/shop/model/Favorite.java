package com.aroma.shop.shop.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="favorite")
@Data
@NoArgsConstructor
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_user")
    private Long userId;
    @JoinColumn(name = "product")
    private Long product;
    private Long size;
    private Long count;

    public Favorite(Long id, Long userId, Long product, Long size, Long count) {
        this.id = id;
        this.userId = userId;
        this.product = product;
        this.size = size;
        this.count = count;
    }
}
