package com.rhas.usermanagement.service;

import com.rhas.usermanagement.dto.DTO;
import com.rhas.usermanagement.entities.User;
import com.rhas.usermanagement.entities.Role;
import com.rhas.usermanagement.repositories.RoleRepository;
import com.rhas.usermanagement.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public DTO.AuthResponse login(DTO.LoginRequest request) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        return new DTO.AuthResponse(jwtService.generateToken((UserDetails) auth.getPrincipal()));
    }

    public void register(DTO.RegisterRequest request) {
        if (userRepo.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("Email ya registrado");
        }

        Role defaultRole = roleRepo.findByName("USER").orElseThrow();

        User user = User.builder()
                .name(request.name())
                .surname(request.surname())
                .email(request.email())
                .phone(request.phone())
                .passwordHash(encoder.encode(request.password()))
                .roles(Set.of(defaultRole))
                .build();

        userRepo.save(user);
    }
}