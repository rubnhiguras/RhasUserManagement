package com.rhas.usermanagement.controller;

import com.rhas.usermanagement.dto.DTO;
import com.rhas.usermanagement.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<DTO.AuthResponse> authenticateUser(@Valid @RequestBody DTO.LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/register")
    public void registerUser(@Valid @RequestBody DTO.RegisterRequest registerRequest) {
        authService.register(registerRequest);
    }
}