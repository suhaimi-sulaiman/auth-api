package com.suhaimisulaiman.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suhaimisulaiman.auth.dto.AuthRequestDto;
import com.suhaimisulaiman.auth.dto.AuthResponseDto;
import com.suhaimisulaiman.auth.dto.UserRegistrationDto;
import com.suhaimisulaiman.auth.model.User;
import com.suhaimisulaiman.auth.service.AuthService;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/registrations")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        User user = authService.registerUser(userRegistrationDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> authenticate(@RequestBody AuthRequestDto authRequestDto) {
        AuthResponseDto authResponseDto = authService.authenticate(authRequestDto);
        return new ResponseEntity<>(authResponseDto, HttpStatus.OK); // Returns a 200 OK response with the
                                                                     // authResponseDto
    }
}
