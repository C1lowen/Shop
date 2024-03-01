package com.aroma.shop.shop.repository;

import com.aroma.shop.shop.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    @Query(value = "select id from brand where brand = :name",nativeQuery = true)
    Long findIdByName(@Param("name") String name);
    @Query(value = "select brand from brand where id = :id",nativeQuery = true)
    String findNameById(@Param("id") Long id);

    @Query (value = "select id from brand limit 4", nativeQuery = true)
    List<Long> idsBrands();
}
