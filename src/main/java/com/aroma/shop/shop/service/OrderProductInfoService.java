package com.aroma.shop.shop.service;

import com.aroma.shop.shop.model.OrderProductInfo;
import com.aroma.shop.shop.repository.OrderProductInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class OrderProductInfoService {
    private final OrderProductInfoRepository orderProductInfoRepository;

    public OrderProductInfoService(OrderProductInfoRepository orderProductInfoRepository) {
        this.orderProductInfoRepository = orderProductInfoRepository;
    }
    @Transactional
    public void save(OrderProductInfo orderProductInfo) {
        orderProductInfoRepository.save(orderProductInfo);
    }
    @Transactional
    public void saveAll(List<OrderProductInfo> orderProductInfos) {
        orderProductInfoRepository.saveAll(orderProductInfos);
    }
}
