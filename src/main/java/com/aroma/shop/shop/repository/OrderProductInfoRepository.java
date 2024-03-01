package com.aroma.shop.shop.repository;

import com.aroma.shop.shop.model.OrderProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductInfoRepository extends JpaRepository<OrderProductInfo, Long> {

}
