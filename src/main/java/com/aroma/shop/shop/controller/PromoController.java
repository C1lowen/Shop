package com.aroma.shop.shop.controller;

import com.aroma.shop.shop.dto.PromoAuthDTO;
import com.aroma.shop.shop.dto.PromoDTO;
import com.aroma.shop.shop.model.Promo;
import com.aroma.shop.shop.service.PromoService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
public class PromoController {
    private final PromoService promoService;

    public PromoController(PromoService promoService) {
        this.promoService = promoService;
    }

    @GetMapping("/activation/promo")
    public ResponseEntity<PromoDTO> activationPromo(
            @RequestParam("print") Optional<String> optionalPrint,
            @RequestParam("local") Optional<String> optionalLocal
    ){
        String print = optionalPrint.orElse("NULLPrint");
        String local = optionalLocal.orElse("NULLLocal");
        return ResponseEntity.ok(promoService.activatePromo(print, local));
    }

    @PostMapping("/promo/save")
    public void savePromo(@RequestBody Promo promo) {
        try {
            promoService.save(promo);
        }catch (RuntimeException e) {
            log.info(e.getMessage());
        }
    }

    @GetMapping("/promo/loadpage/activation/{promo}")
    public ResponseEntity<PromoAuthDTO> loadPagePromo(@PathVariable String promo){
        return ResponseEntity.ok(promoService.loadPagePromo(promo));
    }

}
