package com.rhas.usermanagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private String passwordHash;
    private String avatarUrl;
    // relaciones con roles y permisos

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "usuario_rol",
        joinColumns = @JoinColumn(name = "users.id"),
        inverseJoinColumns = @JoinColumn(name = "roles.id")
    )
    private Set<Role> roles = new HashSet<>();

    public boolean addRol(Role newRole){
        return roles != null ? roles.add(newRole) : new HashSet<>().add(newRole);
    }
}