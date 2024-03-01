package com.aroma.shop.shop.repository;

import com.aroma.shop.shop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "select * from order_cart where id = :codeId",nativeQuery = true)
    Optional<Order> findById(Long codeId);

    @Query(value="SELECT * FROM order_cart WHERE user_id = :id ORDER BY id DESC", nativeQuery = true)
    List<Order> findAllByIdUser(Long id);

    @Query(value = "select * from order_cart where id IN :ids ORDER BY id DESC", nativeQuery = true)
    List<Order> getOrderByListId(List<Long> ids);
}
