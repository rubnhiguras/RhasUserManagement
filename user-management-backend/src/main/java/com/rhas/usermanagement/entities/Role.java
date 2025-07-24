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
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    @Id @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    private String context; // Ej: IT, Deportes
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rol_permiso",
        joinColumns = @JoinColumn(name = "roles.id"),
        inverseJoinColumns = @JoinColumn(name = "permissions.id")
    )
    private Set<Permission> permisos = new HashSet<>();

    public boolean addPermisos(Permission newPermission){
        return permisos != null ? permisos.add(newPermission) :  new HashSet<>().add(newPermission);
    }
}
