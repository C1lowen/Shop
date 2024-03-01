package com.aroma.shop.shop.service;

import com.aroma.shop.shop.model.Specification;
import com.aroma.shop.shop.repository.SpecificationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SpecificationService {
    private final SpecificationRepository specificationRepository;

    public SpecificationService(SpecificationRepository specificationRepository) {
        this.specificationRepository = specificationRepository;
    }

    @Transactional
    public void save(Specification specification){
        specificationRepository.save(specification);
    }

    public Specification getById(Long id) {
       return specificationRepository.findById(id).orElseThrow(() -> new RuntimeException("Ошибка поиска спецификации"));
    }
}
