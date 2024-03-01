package com.aroma.shop.shop.service;
import com.aroma.shop.shop.dto.AuthUser;
import com.aroma.shop.shop.dto.ChangePassword;
import com.aroma.shop.shop.dto.InfoAuthUser;
import com.aroma.shop.shop.dto.UserDTO;
import com.aroma.shop.shop.model.Newsletter;
import com.aroma.shop.shop.model.Role;
import com.aroma.shop.shop.model.User;

import com.aroma.shop.shop.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Pattern;

@Service
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @SneakyThrows
    @Transactional
    public void addUser(UserDTO userDTO) {
        if(userDTO == null) {
            throw new UsernameNotFoundException("User not found");
        }
        String roleName = "User";
        String password = passwordEncoder.encode(userDTO.getPassword());
        Role role = roleService.findByName(roleName).orElseThrow(() -> new Exception(
                "В базе не найдено роли с названием - " + roleName
        ));

        User user = new User(userDTO.getUsername(), password, userDTO.getEmail(), role, null);
        userRepository.save(user);
    }

    @Transactional
    public String saveUserGoogleAuth(User user) {
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        Optional<User> findUser = findUserByEmail(user.getEmail());
        if(findUserByEmail(user.getEmail()).isPresent() && findUser.get().getIdGoogle() == null) {
            return "Пользователь с такой почтой уже существует";
        }
        User userSave = findUserByIdGoogle(user.getIdGoogle())
                .orElseGet(() -> {
                    String roleName = "User";
                    Role role = roleService.findByName(roleName).orElseThrow(() ->
                            new RuntimeException("В базе не найдено роли с названием - " + roleName)
                    );
                    user.setRole(role);
                    user.setPassword("none");
                    return user;
                });
        userRepository.save(userSave);
        return "";
    }

    @Transactional
    public String findUserByDataBase(InfoAuthUser user) {
        Optional<User> userOptional = findUserByEmail(user.getEmail());
        if(userOptional.isPresent()) {
            String passwordByUser = userOptional.get().getPassword();
            String passwordDefault = "none";
            return passwordDefault.equals(passwordByUser) ? "errorEmailWithGoogle" : "";
        }
        return "errorAuth";
    }

    public Boolean isFindGoogleId(String id){
        return userRepository.findGoogleId(id);
    }

    public String getEmailUser() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        if(isFindGoogleId(name)) {
            DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return principal.getAttribute("email");
        }
        return name;
    }
    @Transactional
    public User getAuthUser() throws UsernameNotFoundException{
        String email = getEmailUser();
        Optional<User> user = findUserByEmail(email);
        if (user.isEmpty()) throw new UsernameNotFoundException("Такого пользователя не найдено!");
        return user.get();
    }

    @Transactional
    public User getAuthUserNotException() {
        String email = getEmailUser();
        Optional<User> user = findUserByEmail(email);
        return user.orElse(null);
    }

    @Transactional
    public Optional<User> findUserByIdGoogle(String id) {
        return userRepository.findUserByIdGoogle(id);
    }
    @Transactional
    public String changePass(ChangePassword changePassword){
        String answer = validatePassword(changePassword.getPassOrig(), changePassword.getPassRepeat());
        if(!answer.isEmpty()) return answer;
        Optional<User> user = findActivateCodeByUser(changePassword.getOriginalCode());
        if(user.isEmpty()) return "No such user found";
        update(changePassword.getPassOrig(), user.get());
        user.get().setActivateCode(null);
        userRepository.save(user.get());
        return "";
    }

    @Transactional
    public void update(String newPassword, User user){
        findUserByEmail(user.getEmail()).ifPresent((customUser)  -> {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            UserDetails userDetails = loadUserByUsername(user.getEmail());
            org.springframework.security.core.userdetails.User newUser3 = new org.springframework.security.core.userdetails.User(
                    user.getUsername(), "", userDetails.isEnabled(), userDetails.isAccountNonExpired(), userDetails.isCredentialsNonExpired(), userDetails.isAccountNonLocked(), userDetails.getAuthorities()
            );
            UsernamePasswordAuthenticationToken newToken = new UsernamePasswordAuthenticationToken(newUser3, null, userDetails.getAuthorities());
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(newToken);
        });
    }
    @Transactional
    public Optional<User> findUserByName(String name) {
        return userRepository.findUserByName(name);
    }
    @Transactional
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
    @Transactional
    public Boolean findActivateCode(String code){
        Optional<User> user = userRepository.findActivationCode(code);
        return user.isPresent();
    }

    public String validatePassword(String orig, String repeat){
        if(!orig.equals(repeat)){
            return  "Passwords don't match";
        }else if(orig.length() < 8) {
            return "The password cannot be less than 8 characters";
        }
        return "";
    }
    @Transactional
    public Optional<User> findActivateCodeByUser(String code){
        return userRepository.findActivationCode(code);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findUserByEmail(email).orElseThrow(() -> new RuntimeException(
                "Пользователь с email " + email + " не найден"
        ));

        List<GrantedAuthority> roles = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().getRole()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), roles);
    }
    @Transactional
    public String validateUser(UserDTO user) {
        String regex = "[@. /;'+?!-]";
        String username = user.getUsername();
        String password = user.getPassword();
        String passwordRepeat = user.getPasswordRepeat();
        String email = user.getEmail();
        if(findUserByEmail(email).isPresent())
            return "Пользователь с таким email уже существует!";
        if(username.length() < 3 || username.length() >= 15)
            return "Вы ввели некоретное имя(от 3 до 15 символов)";
        if (Pattern.compile(regex).matcher(username).find())
            return "Имя содержит недопустимые символы";
        if(password.length() < 8)
            return "Слишком короткий пароль! (от 8 символов)";
        if(!passwordRepeat.equals(password))
            return "Пароли не совпадают";

        return "";
    }

    public boolean isAuth() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        return authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);
    }


}
