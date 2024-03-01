package com.aroma.shop.shop.configuration;

import com.aroma.shop.shop.model.Newsletter;
import com.aroma.shop.shop.model.User;
import com.aroma.shop.shop.service.NewsletterService;
import com.aroma.shop.shop.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


import java.io.IOException;
@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {
    private final UserService userService;
    private final NewsletterService newsletterService;

    public AuthSuccessHandler(UserService userService, NewsletterService newsletterService) {
        this.userService = userService;
        this.newsletterService = newsletterService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken oauthUser = (OAuth2AuthenticationToken) authentication;
        OAuth2User principal = oauthUser.getPrincipal();
        User user = new User();
        user.setUsername(principal.getAttribute("name"));
        user.setEmail(principal.getAttribute("email"));
        user.setIdGoogle(principal.getAttribute("sub"));
        newsletterService.save(new Newsletter(null, user.getEmail()));
        String answer = userService.saveUserGoogleAuth(user);
        if (!answer.isEmpty()) {
            HttpSession httpSession = request.getSession(false);
            if (httpSession != null) {
                httpSession.invalidate();
            }
            request.setAttribute("answerText", answer);
            response.sendRedirect("/google/auth");
            return;
        }
        response.sendRedirect("/");
    }
}
