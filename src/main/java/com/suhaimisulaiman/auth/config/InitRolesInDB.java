package com.suhaimisulaiman.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.suhaimisulaiman.auth.model.Role;
import com.suhaimisulaiman.auth.repository.RoleRepository;

@Configuration
public class InitRolesInDB {

    @Autowired
    private RoleRepository roleRepository;

    @Bean
    CommandLineRunner initRoles() {
        return args -> {
            if (roleRepository.findByName("ADMIN") == null) {
                Role adminRole = new Role();
                adminRole.setName("ADMIN");
                roleRepository.save(adminRole);
            }

            if (roleRepository.findByName("USER") == null) {
                Role userRole = new Role();
                userRole.setName("USER");
                roleRepository.save(userRole);
            }
        };
    }
}
