package com.suhaimisulaiman.auth.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.suhaimisulaiman.auth.dto.AuthRequestDto;
import com.suhaimisulaiman.auth.dto.AuthResponseDto;
import com.suhaimisulaiman.auth.dto.UserRegistrationDto;
import com.suhaimisulaiman.auth.exception.AuthenticationException;
import com.suhaimisulaiman.auth.exception.UsernameAlreadyExistsException;
import com.suhaimisulaiman.auth.model.Role;
import com.suhaimisulaiman.auth.model.User;
import com.suhaimisulaiman.auth.repository.RoleRepository;
import com.suhaimisulaiman.auth.repository.UserRepository;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public AuthResponseDto authenticate(AuthRequestDto authRequestDto) {

        logger.info("Authenticating user `{}`", authRequestDto.getUsername());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(),
                            authRequestDto.getPassword()));

            if (authentication.isAuthenticated()) {
                logger.info("Authentication successful for user `{}`", authRequestDto.getUsername());
                return AuthResponseDto.builder()
                        .accessToken(jwtService.generateToken(authRequestDto.getUsername()))
                        .build();
            } else {
                logger.error("Authentication failed for user `{}`", authRequestDto.getUsername());
                throw new AuthenticationException("Authentication failed for user " + authRequestDto.getUsername());
            }
        } catch (Exception e) {
            logger.error("Authentication exception for user `{}`. Error: {}", authRequestDto.getUsername(),
                    e.getMessage());
            throw e;
        }
    }

    @Transactional
    public User registerUser(UserRegistrationDto userRegistrationDto) {
        // Check if the username is already in use
        if (userRepository.existsByUsername(userRegistrationDto.getUsername())) {
            throw new UsernameAlreadyExistsException("Username is already in use");
        }

        // Create a new user
        User user = new User();
        user.setUsername(userRegistrationDto.getUsername());
        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));

        // Assign roles to the user
        Set<Role> roles = new HashSet<>();
        Optional.ofNullable(userRegistrationDto.getRoles()).ifPresent(roleNames -> {
            for (String roleName : roleNames) {
                Role role = roleRepository.findByName(roleName);
                if (role == null) {
                    role = new Role();
                    role.setName(roleName);
                    roleRepository.save(role);
                }
                roles.add(role);
            }
        });

        // If no roles are assigned, assign the default "USER" role
        if (roles.isEmpty()) {
            Role userRole = roleRepository.findByName("USER");
            if (userRole == null) {
                userRole = new Role();
                userRole.setName("USER");
                roleRepository.save(userRole);
            }
            roles.add(userRole);
        }
        user.setRoles(roles);

        // Save the user
        return userRepository.save(user);
    }
}
