package com.suhaimisulaiman.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.suhaimisulaiman.auth.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(String username);

    public boolean existsByUsername(String username);
}