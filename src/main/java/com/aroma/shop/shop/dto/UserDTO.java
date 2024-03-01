package com.aroma.shop.shop.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;
    private String passwordRepeat;
    private String email;
    private long roleId;

    public UserDTO(String password, String email) {
        this.password = password;
        this.email = email;
        this.username = null;
        this.passwordRepeat = null;
    }
}
