package com.aroma.shop.shop.repository;

import com.aroma.shop.shop.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    @Query(value = "select product from favorite where id_user = :user", nativeQuery = true)
    List<Long> findByUserId(Long user);

    @Query(value = "select * from favorite where id_user = :userId", nativeQuery = true)
    List<Favorite> findByUserIdFullObject(Long userId);

    @Query(value = "delete from favorite where id_user = :idUser and product = :idProduct", nativeQuery = true)
    @Modifying
    void deleteByIdAndName(Long idUser, Long idProduct);

    @Query(value = "SELECT EXISTS (select 1 from favorite where id_user = :idUser and product = :idProduct)", nativeQuery = true)
    Boolean findByIdAndName(Long idUser, Long idProduct);

    @Query(value = "select COUNT(id_user) from favorite where id_user = :id", nativeQuery = true)
    Integer countProductByUser(Long id);
}
