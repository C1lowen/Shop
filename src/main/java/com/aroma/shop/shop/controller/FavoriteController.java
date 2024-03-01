package com.aroma.shop.shop.controller;

import com.aroma.shop.shop.dto.*;
import com.aroma.shop.shop.model.User;
import com.aroma.shop.shop.service.FavoriteService;
import com.aroma.shop.shop.service.LikesService;
import com.aroma.shop.shop.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final UserService userService;
    private final LikesService likesService;

    public FavoriteController(FavoriteService favoriteService, UserService userService, LikesService likesService) {
        this.favoriteService = favoriteService;
        this.userService = userService;
        this.likesService = likesService;
    }

    @PostMapping("/addfavorite/{id}")
    @ResponseBody
    public ResponseFavorite addProductFavorite(
            HttpSession session,
            @PathVariable Long id,
            @RequestParam("size") Long size,
            @RequestParam("count") Long count

    ){
        List<SpecificationsProduct> specificationsProducts= favoriteService.addFavorite(session, id, size, count);
        return new ResponseFavorite(userService.isAuth(), specificationsProducts);
    }

    @PostMapping("/findfavorite")
    @ResponseBody
    public AuthCartProduct addProductFavorite(@RequestBody Map<String, Object> payload){
        List<LinkedHashMap<String, Object>> localMaps = (List<LinkedHashMap<String, Object>>) payload.get("local");

        List<SpecificationsProduct> local = localMaps.stream()
                .map(map -> new SpecificationsProduct(
                        ((Number) map.get("productId")).longValue(),
                        ((Number) map.get("size")).longValue(),
                        ((Number) map.get("count")).longValue()
                ))
                .toList();

        return favoriteService.findProducts(local);
    }

    @PostMapping("/deletefavorite/{id}")
    @ResponseBody
    public ResponseFavorite deleteProductFavorite(HttpSession session, @PathVariable Long id){
        return new ResponseFavorite(userService.isAuth(), favoriteService.deleteFavorite(session,id));
    }

    @PostMapping("/retrieve/user/products")
    @ResponseBody
    public ResponseFavAndLikes getProductUser() {
        boolean auth = userService.isAuth();
        if(auth) {
            String email = userService.getEmailUser();
            User user = userService.findUserByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Такого пользователя не найдено"));
            return new ResponseFavAndLikes(true, favoriteService.findByUserId(user.getId()),
                    likesService.findIdProductLikes(user.getId()));
        }
        return new ResponseFavAndLikes(false, null, null);
    }

    @PostMapping("/page/count")
    @ResponseBody
    public CountPage getCountPage() {
        boolean auth = userService.isAuth();
        if(auth) {
            String email = userService.getEmailUser();
            User user = userService.findUserByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Такого пользователя не найдено"));
            return new CountPage(true, favoriteService.findCountProduct(user.getId()), likesService.countLikes(user.getId()));
        }
        return new CountPage(false, null, null);
    }

}
