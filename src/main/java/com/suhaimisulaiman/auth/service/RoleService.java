package com.suhaimisulaiman.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suhaimisulaiman.auth.model.Role;
import com.suhaimisulaiman.auth.repository.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }
}