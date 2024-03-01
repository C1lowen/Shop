package com.aroma.shop.shop.controller;

import com.aroma.shop.shop.dto.ProductDTO;
import com.aroma.shop.shop.model.Products;
import com.aroma.shop.shop.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class ProductController {
    private final ProductsService productsService;
    private final BrandService brandService;
    private final GenderService genderService;
    private final ColorService colorService;
    private final ReviewService reviewService;
    private final SpecificationService specificationService;


    public ProductController(ProductsService productsService, BrandService brandService, GenderService genderService, ColorService colorService, ReviewService reviewService, SpecificationService specificationService) {
        this.productsService = productsService;
        this.brandService = brandService;
        this.genderService = genderService;
        this.colorService = colorService;
        this.reviewService = reviewService;
        this.specificationService = specificationService;
    }

    @GetMapping("shop/product/{id}")
    public String productPage(@PathVariable Long id, Model model){
        Optional<Products> product = productsService.findProductById(id);
        if(product.isEmpty()) return null;
        Products result = product.get();
        Boolean addedToCart = false;

        productsService.findByGroupProduct(model);
        model.addAttribute("buttonAddToCart", addedToCart);
        model.addAttribute("product",  new ProductDTO(result.getId(), result.getName(), result.getImages(),
                        brandService.findById(result.getCategory()), genderService.findById(result.getGender()),
                        colorService.findById(result.getColor()), result.getPrice(),specificationService.getById(id))
                );
        model.addAttribute("reviews", reviewService.findByIdProduct(id));
        model.addAttribute("allReviews", reviewService.findByIdProductAll(id));
        model.addAttribute("averageStars", reviewService.findCountIdProduct(id));
        model.addAttribute("staticStars", reviewService.findStarsCount(id));
        return "single-product";
    }

}
