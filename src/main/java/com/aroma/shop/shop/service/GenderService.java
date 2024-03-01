package com.aroma.shop.shop.service;

import com.aroma.shop.shop.repository.GenderRepository;
import org.springframework.stereotype.Service;

@Service
public class GenderService {
    private final GenderRepository genderRepository;

    public GenderService(GenderRepository genderRepository) {
        this.genderRepository = genderRepository;
    }

    public Long findIdByName(String name) {
        return genderRepository.findIdByName(name);
    }

    public String findById(Long id) {
        return genderRepository.findNameById(id);
    }
}
