package com.rhas.usermanagement.repositories.initializer;

import com.rhas.usermanagement.UserManagementApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.rhas.usermanagement.repositories.UserRepository;
import com.rhas.usermanagement.repositories.PermissionRepository;
import com.rhas.usermanagement.repositories.RoleRepository;
import com.rhas.usermanagement.entities.User;
import com.rhas.usermanagement.entities.Permission;
import com.rhas.usermanagement.entities.Role;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminUserInitializer {

    private static final Logger logger = LoggerFactory.getLogger(AdminUserInitializer.class);

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    private final UserRepository userRepository;
    private final PermissionRepository permisoRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initAdminUser() {
        logger.info("Init base de datos");
        // Verificar si ya existe el usuario admin
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            logger.info("Init permisos y roles");
            // Crear permisos de administrador y roles
            Permission permisoAdmin = Permission.builder()
                    .name("FULL_ACCESS")
                    .description("Acceso total al sistema")
                    .build();

            Role roleAdmin = Role.builder()
                    .name("ROLE_ADMIN")
                    .description("Usuario administrador general")
                    .build();

            roleAdmin.addPermisos(permisoAdmin);

            // Crear usuario administrador
            User admin = User.builder()
                .name("Administrador")
                .phone("+1 (212) 555-1234")
                .email(adminEmail)
    //            .passwordHash(passwordEncoder.encode(adminPassword)) // Siempre codificar la contraseña
                .passwordHash(adminPassword)
                .avatarUrl("https://e7.pngegg.com/pngimages/713/136/png-clipart-computer-icons-system-administrator-id-computer-network-heroes.png")
                .build();

            admin.addRol(roleAdmin);

            logger.info("Init permisos y roles Guardando");

            permisoRepository.save(permisoAdmin);
            roleRepository.save(roleAdmin);
            userRepository.save(admin);

            logger.info("Init permisos y roles HECHO");

        }
    }
}
