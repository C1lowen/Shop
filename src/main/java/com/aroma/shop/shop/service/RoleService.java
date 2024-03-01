package com.aroma.shop.shop.service;

import com.aroma.shop.shop.model.Role;
import com.aroma.shop.shop.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public void addRole(Role role) {
        roleRepository.save(role);
    }
    @Transactional
    public void deleteRoleByName(Long id) {
        roleRepository.deleteById(id);
    }

    @Transactional
    public Optional<Role> findByName(String name) {
       return roleRepository.findByName(name);
    }
}
