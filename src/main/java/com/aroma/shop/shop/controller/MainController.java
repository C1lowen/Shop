package com.aroma.shop.shop.controller;

import com.aroma.shop.shop.dto.*;
import com.aroma.shop.shop.service.*;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@Controller
@Slf4j
public class MainController {

    private final ProductsService productsService;
    private final FavoriteService favoriteService;
    private final LikesService likesService;

    private final ReviewService reviewService;

    private final MailSender mailSender;


    private final OrderService orderService;

    public MainController(ProductsService productsService, FavoriteService favoriteService, LikesService likesService, ReviewService reviewService, MailSender mailSender, OrderService orderService) {
        this.productsService = productsService;
        this.favoriteService = favoriteService;
        this.likesService = likesService;
        this.reviewService = reviewService;
        this.mailSender = mailSender;
        this.orderService = orderService;
    }

    @PostMapping("/send/email/activator")
    @ResponseBody
    public String sendEmailActivator(@RequestParam String email) {
        return mailSender.sendEmailActivator(email);
    }


    @GetMapping("/")
    public String rootPage(Model model) {
        model.addAttribute("popularProduct", reviewService.getPopularProducts());
        model.addAttribute("ourProduct", productsService.findAllLimit());
        log.info("start root page");
        return "index";
    }
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    @GetMapping("/register")
    public String registerPage(){
        return "register";
    }
    @GetMapping("/shop")
    public String shopPage(Model model,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           @RequestParam(value = "gender", required = false) String[] gender,
                           @RequestParam(value = "brands", required = false) String[] brands,
                           @RequestParam(value = "color", required = false) String[] color,
                           @RequestParam(value = "sorted", required = false) String sorted,
                           @RequestParam(value = "priceStart", required = false) Double startPrice,
                           @RequestParam(value = "priceEnd", required = false) Double endPrice){
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(6);

        Page<ProductDTO> productPage = productsService.findPaginated(PageRequest.of(currentPage-1, pageSize),
                        new ConditionProducts(gender, color, brands, startPrice, endPrice, sorted));

        int totalPages = productPage.getTotalPages();
        int sizePage = 4;

        productsService.findByGroupProduct(model);
        model.addAttribute("sizePageElements", pageSize);
        model.addAttribute("genderBox", gender);
        model.addAttribute("sorted", sorted);
        model.addAttribute("brandsBox", brands);
        model.addAttribute("colorBox", color);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("fullCarts", productPage);
        model.addAttribute("lastPage", totalPages);

        if(startPrice == null || endPrice == null)
            model.addAttribute("price", new PriceDTO(0, 1000));
        else
            model.addAttribute("price", new PriceDTO(startPrice, endPrice));

        if (currentPage >= sizePage && currentPage < totalPages)
            productsService.listNumber(currentPage-2,currentPage+1, model);
         else if(totalPages <= sizePage)
            productsService.listNumber(1,totalPages, model);
         else if(currentPage == totalPages)
            productsService.listNumber(currentPage-3,currentPage, model);
         else if (currentPage <= totalPages)
            productsService.listNumber(1,sizePage, model);


        return "category";
    }

    @GetMapping("/cart")
    public String cartPage(HttpSession session, Model model){
        model.addAttribute("cartProduct", favoriteService.findProductBySession(session));
        return "cart";
    }
    @GetMapping("/order/successful")
    public String successfulOrder(@RequestParam("id") Long id, Model model) {
        model.addAttribute("getIdOrder", id);
        return "successful-order";
    }
    @GetMapping("/history")
    public String historyPage(Model model) {
        model.addAttribute("orders", orderService.getAllOrderByUserId());
        return "history";
    }


    @GetMapping("/error404")
    public String errorPage(){
        return "404-found";
    }


    @GetMapping("/shop/product")
    public String productPage(){
        return "single-product";
    }
    @GetMapping("/tracking")
    public String trackingPage(){
        return "tracking-order";
    }
    @GetMapping("/contact")
    public String contactPage(){
        return "contact";
    }
    @GetMapping("/likes")
    public String likesPage(Model model) {
        model.addAttribute("productLikes", likesService.getListProduct());
        return "likes";
    }
}
