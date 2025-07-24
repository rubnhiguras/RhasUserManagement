package com.rhas.usermanagement.dto;

import java.util.Set;

public class DTO {

    // Auth DTOs
    public record LoginRequest(String email, String password) {}
    public record RegisterRequest(String name, String surname, String phone, String email, String password) {}
    public record AuthResponse(String token) {}

    // Response DTOs
    public record PermissionResponse(Long id, String name, String description) {}
    public record RoleResponse(Long id, String name, String context, String description, Set<PermissionResponse> permisos) {}
    public record UserResponse(Long id, String name, String surname, String email, String phone, String avatarUrl, Set<RoleResponse> roles) {}

}
