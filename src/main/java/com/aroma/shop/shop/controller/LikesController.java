package com.aroma.shop.shop.controller;

import com.aroma.shop.shop.model.Likes;
import com.aroma.shop.shop.model.User;
import com.aroma.shop.shop.service.FavoriteService;
import com.aroma.shop.shop.service.LikesService;
import com.aroma.shop.shop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/likes")
public class LikesController {
    private final LikesService likesService;

    public LikesController(LikesService likesService) {
        this.likesService = likesService;
    }

    @GetMapping("/save/{id}")
    public ResponseEntity<?> saveLikes(@PathVariable Long id) {
        try {
            likesService.saveLikes(id);
            return ResponseEntity.ok(true);
        }catch (Exception e){
            log.info(e.getMessage());
            return ResponseEntity.ok(false);
        }
    }
    @GetMapping("/save/cart/{id}")
    public ResponseEntity<?> saveLikesToCart(@PathVariable Long id) {
        try {
            likesService.saveLikesByCart(id);
            return ResponseEntity.ok(true);
        }catch (Exception e){
            log.info(e.getMessage());
            return ResponseEntity.ok(false);
        }
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteLikes(@PathVariable Long id) {
        try {
            likesService.deleteLikes(id);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.ok(false);
        }
    }

    @GetMapping("/delete")
    public ResponseEntity<String> deleteLikes() {
        try{
            String answer = likesService.deleteAllLikesByIdUser();
            return ResponseEntity.ok("{\"answer\": \"" + answer + "\"}");
         }catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.ok("{\"answer\": \"Something went wrong\"}");
         }
    }

    @GetMapping("/save/all")
    public ResponseEntity<String> saveLikesProductsToCart() {
        try {
            Boolean boolAnswer = likesService.saveProductByCart();
            return ResponseEntity.ok("{\"success\": " + boolAnswer + "}");
        }catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.ok("{\"success\": false}");
        }
    }
    @GetMapping("/product/{id}")
    @ResponseBody
    public String findProduct(@PathVariable Long id){
        return "{\"success\": " + likesService.findProduct(id) + "}";
    }
}
