package com.aroma.shop.shop.repository;

import com.aroma.shop.shop.model.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface SpecificationRepository extends JpaRepository<Specification, Long> {
}
