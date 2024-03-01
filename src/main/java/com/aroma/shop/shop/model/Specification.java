package com.aroma.shop.shop.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="specification")
@Data
@NoArgsConstructor
public class Specification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String size;
    private String purpose;
    private String material;
    private Boolean membrane;
    private String country;
    private String description;
    @Column(name = "short_description")
    private String shortDescription;
}
