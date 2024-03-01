package com.aroma.shop.shop.service;

import com.aroma.shop.shop.dto.ProductDTO;
import com.aroma.shop.shop.model.Favorite;
import com.aroma.shop.shop.model.Likes;
import com.aroma.shop.shop.model.Products;
import com.aroma.shop.shop.model.User;
import com.aroma.shop.shop.repository.LikesRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class LikesService {
    private final LikesRepository likesRepository;

    private final UserService userService;
    private final FavoriteService favoriteService;
    private final ProductsService productsService;

    public LikesService(LikesRepository likesRepository, UserService userService, FavoriteService favoriteService, ProductsService productsService) {
        this.likesRepository = likesRepository;
        this.userService = userService;
        this.favoriteService = favoriteService;
        this.productsService = productsService;
    }

    @Transactional
    public void save(Likes like) {
        likesRepository.save(like);
    }

    @Transactional
    public void delete(Likes like) {
        likesRepository.deleteLikes(like.getUserId(), like.getProductId());
    }
    @Transactional
    public void deleteByIdUser(Long idUser){
        likesRepository.deleteByIdUser(idUser);
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    public void saveLikes(Long id){
        User user = findUser();
        if(!likesRepository.findByIdAndName(user.getId(), id)) {
            save(new Likes(user.getId(), id));
        }
    }
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public void saveLikesByCart(Long id){
        User user = findUser();
        if(!favoriteService.findByIdAndName(user.getId(), id)) {
            favoriteService.save(new Favorite(null, user.getId(), id, 0L,1L ));
        }
        delete(new Likes(user.getId(), id));
    }
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public void deleteLikes(Long id) {
        User user = findUser();
        delete(new Likes(user.getId(), id));
    }
    @Transactional(readOnly = true)
    @PreAuthorize("isAuthenticated()")
    public List<Products> getListProduct(){
        User user = findUser();
        return likesRepository.findProductByIdUser(user.getId()).stream()
                .map(idProduct -> productsService.findProductById(idProduct).orElseThrow(() ->
                        new RuntimeException("Такого товара не существует")))
                .toList();
    }
    @Transactional(readOnly = true)
    public List<Long> findIdProductLikes(Long id){
        return likesRepository.findProductByIdUser(id);
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    public Integer countLikes(Long id) {
        return likesRepository.countLikesByUser(id);
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    public Boolean findProduct(Long idProduct) {
        User user = findUser();
        return likesRepository.findByIdAndName(user.getId(), idProduct);
    }
    private User findUser() {
        String email = userService.getEmailUser();
        return userService.findUserByEmail(email).orElseThrow(() ->
                new RuntimeException("Такого пользователя не найдено!"));
    }
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public Boolean saveProductByCart() {
        try {
            User user = userService.getAuthUser();
            List<Long> productsLikesId = findIdProductLikes(user.getId());
            deleteAllLikesByIdUser();
            productsLikesId.stream()
                    .filter(productId -> !favoriteService.findByIdAndName(user.getId(), productId))
                    .forEach(productId -> favoriteService.save(new Favorite(null, user.getId(), productId, 0L, 0L)));
            return true;
        } catch (UsernameNotFoundException e) {
            log.info(e.getMessage());
            return false;
        }

    }
    @Transactional
    public String deleteAllLikesByIdUser(){
        String email = userService.getEmailUser();
        Optional<User> user = userService.findUserByEmail(email);
        if(userService.findUserByEmail(email).isPresent()){
            deleteByIdUser(user.get().getId());
            return  "Your products have been successfully deleted!";
        }
        return "Something went wrong";
    }


}
