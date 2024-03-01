package com.aroma.shop.shop.repository;

import com.aroma.shop.shop.model.Promo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromoRepository extends JpaRepository<Promo, Long> {
    @Query(value = "select * from promo where promo_name = :name",nativeQuery = true)
    Optional<Promo> findPromoByName(String name);
}
