package com.aroma.shop.shop.controller;

import com.aroma.shop.shop.dto.OrderCodeDTO;
import com.aroma.shop.shop.dto.OrderDTO;
import com.aroma.shop.shop.dto.OrdersListDTO;
import com.aroma.shop.shop.dto.ResponseOrder;
import com.aroma.shop.shop.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping("/save")
    public ResponseOrder saveOrder(HttpSession httpSession, @RequestBody OrderDTO order){
        return orderService.save(httpSession, order);
    }

    @GetMapping("/tracking/code/{id}")
    public List<OrderCodeDTO> validateCode(@PathVariable Long id) {
        return orderService.getElementById(id);
    }
    @GetMapping("/get/list")
    public List<OrdersListDTO> getListOrders(@RequestParam("orders") List<Long> orders){
        return orderService.getOrderByListId(orders);
    }
}
