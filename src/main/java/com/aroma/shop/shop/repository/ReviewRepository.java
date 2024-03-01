package com.aroma.shop.shop.repository;

import com.aroma.shop.shop.dto.ProductShortDTO;
import com.aroma.shop.shop.model.Products;
import com.aroma.shop.shop.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(value = "select * from review where id_product = :id order by id DESC LIMIT 3",nativeQuery = true)
    List<Review> findByIdProduct(@Param("id") Long id);

    @Query(value = "select * from review where id_product = :id order by id DESC",nativeQuery = true)
    List<Review> findByIdProductAll(Long id);
    @Query(value = "select * from review where id_product = :id order by id DESC OFFSET 3",nativeQuery = true)
    List<Review> findByIdProductStart(Long id);

    @Query(value = "SELECT CAST(SUM(stars) AS double precision) / CAST(COUNT(stars) AS double precision) FROM review WHERE id_product = :id", nativeQuery = true)
    Optional<Double> findCountIdProduct(Long id);

    @Query(value = "SELECT id_product FROM review GROUP BY id_product ORDER BY CAST(SUM(stars) AS double precision) / CAST(COUNT(stars) AS double precision) DESC LIMIT 8", nativeQuery = true)
    List<Long> ratingPopularProducts();

    @Query(value = "SELECT stars FROM review WHERE id_product = :id", nativeQuery = true)
    List<Integer> findStars(Long id);
}
