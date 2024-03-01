package com.aroma.shop.shop.repository;


import com.aroma.shop.shop.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Products,Long> {
    @Query(value = "select * from products ORDER BY price",nativeQuery = true)
    List<Products> findCheapToDearPrice();
    @Query(value = "select * from products ORDER BY price DESC",nativeQuery = true)
    List<Products> findDearToCheapPrice();

    @Query(value = "select * from products LIMIT 8",nativeQuery = true)
    List<Products> findAllLimit();

    @Query(value = "SELECT * FROM ( " +
            "  SELECT *, ROW_NUMBER() OVER (PARTITION BY category ORDER BY category) as row_num " +
            "  FROM products " +
            "  WHERE category IN :idProducts " +
            ") AS subquery " +
            "WHERE row_num <= 3", nativeQuery = true)
    List<Products> findByGroupProducts(@Param("idProducts") List<Long> idProducts);

    @Query(value = "select COUNT(*) from products", nativeQuery = true)
    Long findAllProductCount();


}
