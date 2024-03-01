package com.aroma.shop.shop.service;

import com.aroma.shop.shop.dto.PromoAuthDTO;
import com.aroma.shop.shop.dto.PromoDTO;
import com.aroma.shop.shop.model.Promo;
import com.aroma.shop.shop.model.PromoUser;
import com.aroma.shop.shop.model.User;
import com.aroma.shop.shop.repository.PromoRepository;
import com.aroma.shop.shop.repository.PromoUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class PromoService {
    private final PromoRepository promoRepository;
    private final UserService userService;
    private final PromoUserRepository promoUserRepository;
    private final FavoriteService favoriteService;

    public PromoService(PromoRepository promoRepository, UserService userService, PromoUserRepository promoUserRepository, FavoriteService favoriteService) {
        this.promoRepository = promoRepository;
        this.userService = userService;
        this.promoUserRepository = promoUserRepository;
        this.favoriteService = favoriteService;
    }

    @Transactional
    public void save(Promo promo){
        if(promo == null) {
            throw new RuntimeException("Promo cannot be empty");
        }
        promo.setPromo(generatePromo());
        promoRepository.save(promo);
    }

    @Transactional
    public PromoDTO activatePromo(String print, String local) {
        Optional<Promo> optionalPromo = promoRepository.findPromoByName(print);

        if (optionalPromo.isPresent()) {
            Promo objectPromo = optionalPromo.get();
            boolean isAuth = userService.isAuth();
            User user = isAuth ? userService.getAuthUser() : new User();
            String answer;

            if ((!isAuth && print.equals(local)) ||
                    promoUserRepository.findUserAndPromo(user.getId(), objectPromo.getId())) {
                answer = "You have already activated this promo";
            } else if (objectPromo.getActivateDate().isBefore(LocalDate.now())) {
                answer = "This promo code is no longer active";
            } else if (objectPromo.getCountActivation() <= 0) {
                answer = "Promo has exceeded activation";
            } else {
                if (isAuth) {
                    PromoUser promoUser = new PromoUser();
                    promoUser.setPromoId(objectPromo.getId());
                    promoUser.setUserId(user.getId());
                    promoUserRepository.save(promoUser);
                }

                objectPromo.setCountActivation(objectPromo.getCountActivation() - 1);
                promoRepository.save(objectPromo);
                return new PromoDTO(true, isAuth, objectPromo.getDiscount(), objectPromo.getPromo(), null);
            }

            return new PromoDTO(false, isAuth, objectPromo.getDiscount(), objectPromo.getPromo(), answer);
        }

        return new PromoDTO(false, false, 0, "", "Promo does not exist");
    }

    @Transactional
    public PromoAuthDTO loadPagePromo(String promo){
        if(userService.isAuth()) {
            User user = userService.getAuthUser();
            Optional<PromoUser> idPromo = promoUserRepository.findById(user.getId());
            if(idPromo.isPresent()) {
                Optional<Promo> optionalPromo = promoRepository.findById(idPromo.get().getPromoId());
                if(optionalPromo.isPresent()){
                    Promo objectPromo = optionalPromo.get();
                    return new PromoAuthDTO(true,true, objectPromo.getDiscount(), null);
                }
            }
        }else {
            return validatePromo(promo);
        }
        return new PromoAuthDTO();
    }

    private PromoAuthDTO validatePromo(String promo){
        Optional<Promo> optionalPromo = promoRepository.findPromoByName(promo);
        if(optionalPromo.isPresent()){
            Promo objPromo = optionalPromo.get();
            Integer countActivate =  objPromo.getCountActivation();
            LocalDate date = objPromo.getActivateDate();
            if(countActivate > 0 && date.isAfter(LocalDate.now())){
                return new PromoAuthDTO(false, true, objPromo.getDiscount(), null);
            }
        }
        return new PromoAuthDTO(false, false, 0, null);
    }

    private String generatePromo(){
        String promo;
        do {
            promo = RandomStringUtils.random(6, true, true);
        } while(promoRepository.findPromoByName(promo).isPresent());

        return promo;
    }
}
