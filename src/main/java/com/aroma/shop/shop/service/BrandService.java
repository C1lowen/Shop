package com.aroma.shop.shop.service;

import com.aroma.shop.shop.repository.BrandRepository;
import com.aroma.shop.shop.repository.ColorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public Long findIdByName(String name) {
        return brandRepository.findIdByName(name);
    }

    public String findById(Long id) {
        return brandRepository.findNameById(id);
    }

    public List<Long> idsBrands() {
        return brandRepository.idsBrands();
    }
}
