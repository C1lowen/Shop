package com.aroma.shop.shop.service;

import com.aroma.shop.shop.dto.*;
import com.aroma.shop.shop.model.Order;
import com.aroma.shop.shop.model.OrderProductInfo;
import com.aroma.shop.shop.model.Products;
import com.aroma.shop.shop.model.User;
import com.aroma.shop.shop.repository.OrderRepository;
import com.aroma.shop.shop.repository.PromoUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductsService productsService;
    private final UserService userService;
    private final FavoriteService favoriteService;

    private final PromoUserRepository promoUserRepository;

    public OrderService(OrderRepository orderRepository, ProductsService productsService, UserService userService, FavoriteService favoriteService, PromoUserRepository promoUserRepository) {
        this.orderRepository = orderRepository;
        this.productsService = productsService;
        this.userService = userService;
        this.favoriteService = favoriteService;
        this.promoUserRepository = promoUserRepository;
    }

    @Transactional
    public List<OrderCodeDTO> getElementById(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);

        if (orderOptional.isEmpty()) {
            return new ArrayList<>();
        }

        Order order = orderOptional.get();

        return order.getProducts().stream()
                .map(orderElem -> new OrderCodeDTO(
                        orderElem.getProducts().getName(),
                        Integer.parseInt(String.valueOf(orderElem.getCount())),
                        (int) (orderElem.getProducts().getPrice() * Integer.parseInt(String.valueOf(orderElem.getCount()))),
                        orderElem.getProducts().getImages()))
                .toList();
    }

    @Transactional
    public ResponseOrder save(HttpSession session, OrderDTO orderDTO) {
        if(orderDTO == null) return new ResponseOrder(null,null, null, false);
        try {
            ResponseOrder responseOrder = new ResponseOrder();
            List<OrderProductInfo> listProduct = orderDTO.getProducts().stream()
                    .map(products -> productsService.findProductById(products.getId())
                            .map(activeProduct -> new OrderProductInfo(activeProduct, products.getCount(), products.getSize()))
                            .orElseThrow(() -> new NoSuchElementException("Product not found for ID: " + products.getId())))
                    .toList();

            User user = userService.getAuthUserNotException();
            Long idUser = (user == null) ? null : user.getId();

            if(userService.isAuth()) {
                listProduct.forEach(product -> favoriteService.deleteByIdAndName(user.getId(), product.getProducts().getId()));
                responseOrder.setIsAuth(true);
                responseOrder.setDeleteLocalProduct(false);
                promoUserRepository.deletePromoByUser(user.getId());
            }else {
                responseOrder.setIsAuth(false);
                responseOrder.setDeleteLocalProduct(true);
                session.setAttribute("favorite", null);
            }

            Order order = new Order(idUser, orderDTO.getPrice(),
                    orderDTO.getDiscount(), listProduct, orderDTO.getPhone(),
                    orderDTO.getDelivery(), orderDTO.getDeliveryPrice());

            Order orderSaveDB = orderRepository.save(order);
            responseOrder.setId(orderSaveDB.getId());
            responseOrder.setValidResponse(true);

            return responseOrder;
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseOrder(null,null, null, false);
        }
    }
    @Transactional
    public List<OrdersListDTO> getAllOrderByUserId() {
        if(userService.isAuth()) {
            User user = userService.getAuthUser();
            return orderRepository.findAllByIdUser(user.getId()).stream()
                    .map(order -> new OrdersListDTO(order.getId(),
                            order.getProducts().stream()
                                    .map(product -> new ProductOrdersInfo(product.getProducts().getName(),
                                            product.getProducts().getImages(), product.getSize(), product.getCount(),
                                            product.getProducts().getPrice()))
                                    .toList(),
                                    order.getPrice(), order.getDiscount()))
                    .toList();
        }
        return new ArrayList<>();
    }

    @Transactional
    public List<OrdersListDTO> getOrderByListId(List<Long> orders) {
        if(userService.isAuth()) return new ArrayList<>();
        return orderRepository.getOrderByListId(orders).stream()
                .map(order -> new OrdersListDTO(order.getId(),
                        order.getProducts().stream()
                                .map(product -> new ProductOrdersInfo(product.getProducts().getName(),
                                        product.getProducts().getImages(), product.getSize(), product.getCount(),
                                        product.getProducts().getPrice()))
                                .toList(),
                        order.getPrice(), order.getDiscount()))
                .toList();
    }
}
