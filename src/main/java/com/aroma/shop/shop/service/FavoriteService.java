package com.aroma.shop.shop.service;

import com.aroma.shop.shop.dto.AuthCartProduct;
import com.aroma.shop.shop.dto.FavoriteDTO;
import com.aroma.shop.shop.dto.SpecificationsProduct;
import com.aroma.shop.shop.model.Favorite;
import com.aroma.shop.shop.model.Products;
import com.aroma.shop.shop.model.User;
import com.aroma.shop.shop.repository.FavoriteRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final ProductsService productsService;

    private final UserService userService;

    public FavoriteService(FavoriteRepository favoriteRepository, ProductsService productsService, UserService userService) {
        this.favoriteRepository = favoriteRepository;
        this.productsService = productsService;
        this.userService = userService;
    }

    @Transactional
    public List<FavoriteDTO> findProductBySession(HttpSession session) {
        List<Favorite> listIdProduct;

        if(userService.isAuth()) {
            String email = userService.getEmailUser();
            User user = userService.findUserByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Такого пользователя не найдено"));
            listIdProduct = favoriteRepository.findByUserIdFullObject(user.getId());
        }else {
             listIdProduct = new ArrayList<>();
        }
        return listIdProduct.stream()
                .map(favorite -> new FavoriteDTO(favorite.getId(),
                        productsService.findProductById(favorite.getProduct()).orElse(new Products()),
                        favorite.getSize(), favorite.getCount()))
                .toList();

    }

    @Transactional
    public void save(Favorite favorite){
        favoriteRepository.save(favorite);
    }

    @Transactional
    public List<Long> findByUserId(Long id){
       return favoriteRepository.findByUserId(id);
    }
    @Transactional
    public Integer findCountProduct(Long id) {
        return favoriteRepository.countProductByUser(id);
    }

    @Transactional
    public List<SpecificationsProduct> addFavorite(HttpSession session, Long productId, Long size, Long count) {
        if(userService.isAuth()) {
            String email = userService.getEmailUser();
            User user = userService.findUserByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Такого пользователя не найдено"));
            List<Long> listIdProduct = favoriteRepository.findByUserId(user.getId());
            if(!listIdProduct.contains(productId)) {
                listIdProduct.add(productId);
                favoriteRepository.save(new Favorite(null, user.getId(), productId, size, count));
            }
            int allProductSize = favoriteRepository.findByUserId(user.getId()).size();
            return new ArrayList<>(Collections.nCopies(allProductSize, new SpecificationsProduct()));

        }
        List<SpecificationsProduct> listIdProduct = (List<SpecificationsProduct>) session.getAttribute("favorite");
        if (listIdProduct == null || listIdProduct.size() == 0) { listIdProduct = new ArrayList<>();}
        SpecificationsProduct specificationsProduct = new SpecificationsProduct(productId, size, count);
        if(!listIdProduct.contains(specificationsProduct)) {
            listIdProduct.add(specificationsProduct);
            session.setAttribute("favorite", listIdProduct);
        }
        return listIdProduct;
    }
    @Transactional
    public List<SpecificationsProduct> deleteFavorite(HttpSession session, Long productId){
        if(userService.isAuth()) {
            String email = userService.getEmailUser();
            User user = userService.findUserByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Такого пользователя не найдено"));
            List<Long> listIdProduct = favoriteRepository.findByUserId(user.getId());
            if(listIdProduct.contains(productId)) {
                listIdProduct.remove(productId);
                favoriteRepository.deleteByIdAndName(user.getId(), productId);
            }
            int allProductSize = favoriteRepository.findByUserId(user.getId()).size();
            return new ArrayList<>(Collections.nCopies(allProductSize, new SpecificationsProduct()));
        }
        List<SpecificationsProduct> listIdProduct = (List<SpecificationsProduct>) session.getAttribute("favorite");
        if (listIdProduct == null) listIdProduct = new ArrayList<>();

        listIdProduct = listIdProduct.stream()
                .filter(product -> !product.getProductId().equals(productId))
                .toList();
        session.setAttribute("favorite", listIdProduct);

        return listIdProduct;
    }
    @Transactional
    public void deleteByIdAndName(Long userId, Long productId){
        favoriteRepository.deleteByIdAndName(userId, productId);
    }

    @Transactional(readOnly = true)
    public AuthCartProduct findProducts(List<SpecificationsProduct> local) {
        if (!userService.isAuth()) {
            List<FavoriteDTO> products = local.stream()
                    .map(productSpec -> {
                        Products product = productsService.findProductById(productSpec.getProductId()).orElse(new Products());
                        return new FavoriteDTO(null, product, productSpec.getSize(), productSpec.getCount());
                    })
                    .toList();

            return new AuthCartProduct(false, products);
        }
        return new AuthCartProduct(true, new ArrayList<>());
    }

    @Transactional
    public Boolean findByIdAndName(Long idUser, Long idProduct){
        return favoriteRepository.findByIdAndName(idUser,idProduct);
    }

 }
