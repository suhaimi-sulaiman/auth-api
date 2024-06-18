package com.suhaimisulaiman.auth.userdetails;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.suhaimisulaiman.auth.model.User;

public class CustomUserDetails implements UserDetails {

    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().toUpperCase()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Or delegate to user.isAccountNonExpired()
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Or delegate to user.isAccountNonLocked()
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Or delegate to user.isCredentialsNonExpired()
    }

    @Override
    public boolean isEnabled() {
        return true; // Or delegate to user.isEnabled()
    }

    @Override
    public String toString() {
        return "CustomUserDetails{" +
                "username='" + getUsername() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", authorities=" + getAuthorities() +
                ", accountNonExpired=" + isAccountNonExpired() +
                ", accountNonLocked=" + isAccountNonLocked() +
                ", credentialsNonExpired=" + isCredentialsNonExpired() +
                ", enabled=" + isEnabled() +
                '}';
    }
}