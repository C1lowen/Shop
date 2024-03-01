package com.aroma.shop.shop.repository;

import com.aroma.shop.shop.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Long> {
    @Query(value = "select id from gender where gender = :name",nativeQuery = true)
    Long findIdByName(@Param("name") String name);

    @Query(value = "select gender from gender where id = :id",nativeQuery = true)
    String findNameById(@Param("id") Long id);
}
