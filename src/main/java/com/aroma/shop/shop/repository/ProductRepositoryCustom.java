package com.aroma.shop.shop.repository;

import com.aroma.shop.shop.dto.ConditionProducts;
import com.aroma.shop.shop.dto.ProductDTO;
import com.aroma.shop.shop.model.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepositoryCustom {
    Page<ProductDTO> findProductByFilter(ConditionProducts filter, Pageable pageable);
}
