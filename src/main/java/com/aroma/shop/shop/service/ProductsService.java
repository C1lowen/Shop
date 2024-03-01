package com.aroma.shop.shop.service;

import com.aroma.shop.shop.dto.ConditionProducts;
import com.aroma.shop.shop.dto.ProductDTO;
import com.aroma.shop.shop.dto.ProductShortDTO;
import com.aroma.shop.shop.model.Products;
import com.aroma.shop.shop.repository.ProductRepositoryCustomImpl;
import com.aroma.shop.shop.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ProductsService {

    private final ProductsRepository productsRepository;

    private final ProductRepositoryCustomImpl productRepositoryCustom;
    private final BrandService brandService;
    private final GenderService genderService;
    private final ColorService colorService;
    private final SpecificationService specificationService;

    public ProductsService(ProductsRepository productsRepository, ProductRepositoryCustomImpl productRepositoryCustom,
                           BrandService brandService, GenderService genderService,
                           ColorService colorService, SpecificationService specificationService) {
        this.productsRepository = productsRepository;
        this.productRepositoryCustom = productRepositoryCustom;
        this.brandService = brandService;
        this.genderService = genderService;
        this.colorService = colorService;
        this.specificationService = specificationService;
    }

    public void saveProducts(Products products) {
        productsRepository.save(products);
    }
    public List<Products> findAllProducts(){
        return productsRepository.findAll();
    }

    public List<Products> findCheapToDearPrice(){
        return productsRepository.findCheapToDearPrice();
    }

    public List<Products> findDearToCheapPrice(){
       return productsRepository.findDearToCheapPrice();
    }

    public Page<ProductDTO> findByCondition(ConditionProducts filter, Pageable pageable){
        return productRepositoryCustom.findProductByFilter(filter, pageable);
    }

    public List<ProductDTO> findAllLimit() {
        return productsRepository.findAllLimit().stream()
                .map(products -> new ProductDTO(products.getId(), products.getName(), products.getImages(),
                        brandService.findById(products.getCategory()), genderService.findById(products.getGender()),
                        colorService.findById(products.getColor()), products.getPrice(), products.getSpecification())).toList();
    }
    public Page<ProductDTO> findPaginated(Pageable pageable, ConditionProducts filter) {
        return findByCondition(filter, pageable);
    }

    public Long findAllProductCount(){
        return productsRepository.findAllProductCount();
    }
    public void findByGroupProduct(Model model) {
        List<Long> categoryIds = brandService.idsBrands();
        List<ProductShortDTO> adidasProducts = new ArrayList<>();
        List<ProductShortDTO> newBalanceProducts = new ArrayList<>();
        List<ProductShortDTO> skechersProducts = new ArrayList<>();
        List<ProductShortDTO> nikeProducts = new ArrayList<>();

        List<Products> allProducts = productsRepository.findByGroupProducts(categoryIds);

        for (Products product : allProducts) {
            if (product.getCategory().equals(categoryIds.get(0))) {
                adidasProducts.add(new ProductShortDTO(product.getId(), product.getName(), product.getImages(), product.getPrice()));
            } else if (product.getCategory().equals(categoryIds.get(1))) {
                newBalanceProducts.add(new ProductShortDTO(product.getId(), product.getName(), product.getImages(), product.getPrice()));
            } else if (product.getCategory().equals(categoryIds.get(2))) {
                skechersProducts.add(new ProductShortDTO(product.getId(), product.getName(), product.getImages(), product.getPrice()));
            } else if (product.getCategory().equals(categoryIds.get(3))) {
                nikeProducts.add(new ProductShortDTO(product.getId(), product.getName(), product.getImages(), product.getPrice()));
            }
        }

        model.addAttribute("adidas", adidasProducts);
        model.addAttribute("newBalance", newBalanceProducts);
        model.addAttribute("skechers", skechersProducts);
        model.addAttribute("nike", nikeProducts);
    }
    public void listNumber(Integer firstNum,Integer lastNum, Model model){
        List<Integer> pageNumbers = IntStream.rangeClosed(firstNum, lastNum)
                .boxed()
                .collect(Collectors.toList());
        model.addAttribute("pageNumbers", pageNumbers);
    }
    public Optional<Products> findProductById(Long id){
        return productsRepository.findById(id);
    }
}
