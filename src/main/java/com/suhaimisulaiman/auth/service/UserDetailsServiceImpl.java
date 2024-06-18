package com.suhaimisulaiman.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.suhaimisulaiman.auth.model.User;
import com.suhaimisulaiman.auth.repository.UserRepository;
import com.suhaimisulaiman.auth.userdetails.CustomUserDetails;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Attempting to load user by username: {}", username);

        User user = userRepository.findByUsername(username);
        if (user == null) {
            logger.error("User `{}` is not found!", username);
            throw new UsernameNotFoundException("Could not find user with username: " + username);
        }

        logger.info("User `{}` is found", username);
        return new CustomUserDetails(user);
    }
}
