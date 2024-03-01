package com.aroma.shop.shop.repository;

import com.aroma.shop.shop.model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    @Query(value = "select product_id from likes where user_id = :id", nativeQuery = true)
    List<Long> findProductByIdUser(Long id);
    @Query(value = "delete from likes where user_id = :idUser and product_id = :idProduct", nativeQuery = true)
    @Modifying
    void deleteLikes(Long idUser, Long idProduct);

    @Query(value = "SELECT EXISTS (select 1 from likes where user_id = :idUser and product_id = :idProduct)", nativeQuery = true)
    Boolean findByIdAndName(Long idUser, Long idProduct);

    @Query(value = "select COUNT(user_id) from likes where user_id = :id", nativeQuery = true)
    Integer countLikesByUser(Long id);

    @Query(value = "delete from likes where user_id = :id", nativeQuery = true)
    @Modifying
    void deleteByIdUser(Long id);


}
