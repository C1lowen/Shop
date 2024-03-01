package com.aroma.shop.shop.repository;

import com.aroma.shop.shop.model.Newsletter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsletterRepository extends JpaRepository<Newsletter, Long> {
    @Query(value = "SELECT EXISTS (select 1 from newsletter where email = :email)",nativeQuery = true)
    Boolean findByEmail(String email);

    @Query(value ="select email from newsletter", nativeQuery = true)
    List<String> findByAllEmail();
}
