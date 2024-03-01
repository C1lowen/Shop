package com.aroma.shop.shop.repository;

import com.aroma.shop.shop.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
    @Query(value = "select id from color where color = :name",nativeQuery = true)
    Long findIdByName(@Param("name") String name);
    @Query(value = "select color from color where id = :id",nativeQuery = true)
    String findNameById(@Param("id") Long id);
}
