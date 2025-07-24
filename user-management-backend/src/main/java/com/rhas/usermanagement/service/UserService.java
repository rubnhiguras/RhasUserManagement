package com.rhas.usermanagement.service;

import com.rhas.usermanagement.dto.DTO;
import com.rhas.usermanagement.entities.User;
import com.rhas.usermanagement.repositories.UserRepository;
import com.sun.security.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * Get currently authenticated user
     */
    public DTO.UserResponse getCurrentUser() {
        UserPrincipal currentUser = getCurrentUserPrincipal();
        User user = userRepository.findByEmail(currentUser.getName())
                .orElseThrow(() -> new OpenApiResourceNotFoundException(currentUser.getName()));

        return mapUserToUserResponse(user);
    }

    /**
     * Get all users (for admin purposes)
     */
    public List<DTO.UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapUserToUserResponse)
                .collect(Collectors.toList());
    }



    /**
     * Update user profile
     */
    public DTO.UserResponse updateUser(DTO.UserResponse userResponse) {
        UserPrincipal currentUser = getCurrentUserPrincipal();
        User user = userRepository.findByEmail(currentUser.getName())
                .orElseThrow(() -> new OpenApiResourceNotFoundException(currentUser.getName()));

        user.setName(userResponse.name());
        user.setSurname(userResponse.surname());
        user.setPhone(userResponse.phone());
        user.setAvatarUrl(userResponse.avatarUrl());

        User updatedUser = userRepository.save(user);
        return mapUserToUserResponse(updatedUser);
    }

    /**
     * Helper method to get current authenticated user
     */
    private UserPrincipal getCurrentUserPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserPrincipal) authentication.getPrincipal();
    }

    /**
     * Map User entity to UserResponse DTO
     */
    private DTO.UserResponse mapUserToUserResponse(User user) {
        return new DTO.UserResponse(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPhone(),
                user.getAvatarUrl(),
                user.getRoles().stream().map(role ->
                        new DTO.RoleResponse(
                                role.getId(),
                                role.getName(),
                                role.getContext(),
                                role.getDescription(),
                                role.getPermisos().stream().map(permission ->
                                        new DTO.PermissionResponse(
                                                permission.getId(),
                                                permission.getName(),
                                                permission.getDescription()
                                        )
                                ).collect(Collectors.toSet())
                        )
                ).collect(Collectors.toSet())
        );
    }
}