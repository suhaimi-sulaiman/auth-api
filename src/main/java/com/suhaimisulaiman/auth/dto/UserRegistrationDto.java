package com.suhaimisulaiman.auth.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserRegistrationDto {
    private String username;
    private String password;
    private Set<String> roles = new HashSet<>();
}
