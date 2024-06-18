package com.suhaimisulaiman.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suhaimisulaiman.auth.model.Role;
import com.suhaimisulaiman.auth.service.RoleService;

@RestController
@RequestMapping("/api/v1")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/roles")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        Role savedRole = roleService.saveRole(role);
        return new ResponseEntity<>(savedRole, HttpStatus.CREATED);
    }
}