package com.aroma.shop.shop.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Entity(name = "products")
@Data
@NoArgsConstructor
public class Products{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String images;
    private Long category;
    private Long gender;
    private Long color;
    private Double price;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "specification")
    private Specification specification;
}
