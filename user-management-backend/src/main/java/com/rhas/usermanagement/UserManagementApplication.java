package com.rhas.usermanagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserManagementApplication {
    private static final Logger logger = LoggerFactory.getLogger(UserManagementApplication.class);
    public static void main(String[] args) {
        logger.info("[USER MANAGEMENT BACKEND LOGS] 🚀 Iniciando User Management App...");
        SpringApplication.run(UserManagementApplication.class, args);
    }
}
