package com.aroma.shop.shop.service;

import com.aroma.shop.shop.repository.ColorRepository;
import com.aroma.shop.shop.repository.GenderRepository;
import org.springframework.stereotype.Service;

@Service
public class ColorService {
    private final ColorRepository colorRepository;

    public ColorService(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    public Long findIdByName(String name) {
        return colorRepository.findIdByName(name);
    }

    public String findById(Long id) {
        return colorRepository.findNameById(id);
    }
}
