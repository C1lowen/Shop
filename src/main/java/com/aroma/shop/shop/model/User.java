package com.aroma.shop.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;
    @Column(name = "id_google")
    private String idGoogle;
    @Column(name = "activate_code")
    private String activateCode;

    public User(String username, String password, String email, Role role, String idGoogle) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.idGoogle = idGoogle;
    }
}
