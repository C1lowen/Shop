package com.aroma.shop.shop.configuration;


import com.aroma.shop.shop.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CheckAuthFilter extends OncePerRequestFilter {

    private final UserService userService;


    public CheckAuthFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !isOAuthAuthentication(authentication)) {
            String email = authentication.getName();

            if (userService.findUserByEmail(email).isEmpty()) {
                HttpSession httpSession = request.getSession(false);
                if (httpSession != null) {
                    httpSession.invalidate();
                }
                response.sendRedirect("/");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isOAuthAuthentication(Authentication authentication) {
        boolean result = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().startsWith("OIDC_USER"));
        return result;
    }
}
