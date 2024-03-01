package com.aroma.shop.shop.repository;

import com.aroma.shop.shop.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(value = "select * from roles where role = :name",nativeQuery = true)
    Optional<Role> findByName(@Param("name")String name);
}
