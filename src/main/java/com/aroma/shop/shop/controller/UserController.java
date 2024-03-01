package com.aroma.shop.shop.controller;

import com.aroma.shop.shop.dto.*;
import com.aroma.shop.shop.model.Newsletter;
import com.aroma.shop.shop.model.User;
import com.aroma.shop.shop.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    private final UserService userService;
    private final MainService mainService;
    private final FavoriteService favoriteService;
    private final NewsletterService newsletterService;


    public UserController(UserService userService, MainService mainService, FavoriteService favoriteService, NewsletterService newsletterService) {
        this.userService = userService;
        this.mainService = mainService;
        this.favoriteService = favoriteService;
        this.newsletterService = newsletterService;
    }

    @PostMapping("/checksaveuser")
    @ResponseBody
    public ResponseEntity<String> saveUser(@RequestBody UserDTO user, HttpServletRequest request, Model model){
        String answer = userService.validateUser(user);
        String answerForClient = "{\"success\": false, \"answer\": \"" + answer + "\"}";
        if(!answer.isEmpty()) return ResponseEntity.ok(answerForClient);
        String password = user.getPassword();
        userService.addUser(user);
        mainService.userAuth(request, user, password);
        newsletterService.save(new Newsletter(null, user.getEmail()));
        return ResponseEntity.ok("{\"success\": true, \"answer\": \"\"}");
    }

    @GetMapping("/check/auth")
    @ResponseBody
    public Boolean checkAuth(){
        return userService.isAuth();
    }


    @PostMapping("/logins")
    public String authUser(HttpServletRequest request, InfoAuthUser user, Model model) {
        String answerFindUserByDataBase = userService.findUserByDataBase(user);
        if(!answerFindUserByDataBase.isEmpty()) {
            model.addAttribute(answerFindUserByDataBase, true);
            return "login";
        }
        String redirectEndpoint = mainService.userAuth(request, new UserDTO(user.getPassword(), user.getEmail()), user.getPassword());
        return "redirect:" + redirectEndpoint;
    }

    @PostMapping("/authuser/{id}")
    @ResponseBody
    public AuthUser authUser(@PathVariable Long id){
        boolean auth = userService.isAuth();
        if(auth) {
            String email = userService.getEmailUser();
            User user = userService.findUserByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Такого пользователя не найдено"));
            boolean addedToCart = favoriteService.findByIdAndName(user.getId(),id);
            return new AuthUser(true, addedToCart);
        }
        return new AuthUser(false, false);
    }

    @GetMapping("/verif/email")
    public String varifEmailPage(){
        return "emailverif-send";
    }

    @PostMapping("/change/password")
    @ResponseBody
    public ResponseChangePass changePassword(@RequestBody ChangePassword changePassword) {
        String answer = userService.changePass(changePassword);
        return answer.isEmpty() ? new ResponseChangePass(true, "login") : new ResponseChangePass(false, answer);
    }

    @GetMapping("/activate/{code}")
    public String activationLink(@PathVariable String code) {
        return userService.findActivateCode(code) ? "change-password" : "redirect:/error404";
    }

    @GetMapping("/google/auth")
    public String googleAuth(Model model){
        model.addAttribute("errorAuthGoogle", true);
        return "login";
    }

}
