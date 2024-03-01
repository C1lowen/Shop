package com.aroma.shop.shop.repository;

import com.aroma.shop.shop.model.PromoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoUserRepository extends JpaRepository<PromoUser, Long> {
    @Query(value = "SELECT EXISTS (select 1 from promo_user where id_user = :userId and id_promo = :promoId)",nativeQuery = true)
    Boolean findUserAndPromo(Long userId, Long promoId);

    @Query(value = "delete from promo_user where id_user = :id", nativeQuery = true)
    @Modifying
    void deletePromoByUser(Long id);
}
