# RhasUserManagement  

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)  
[![GitHub Issues](https://img.shields.io/github/issues/rubnhiguras/RhasUserManagement)](https://github.com/rubnhiguras/RhasUserManagement/issues)  
[![GitHub Stars](https://img.shields.io/github/stars/rubnhiguras/RhasUserManagement)](https://github.com/rubnhiguras/RhasUserManagement/stargazers)  

**RhasUserManagement** es un sistema de gestión de usuarios diseñado para facilitar el registro, autenticación y administración de usuarios en aplicaciones web.  

## Características  

✅ **Registro y Autenticación de Usuarios**  
✅ **Gestión de Perfiles, Roles y Permisos**  
✅ **Recuperación de Contraseña**  
✅ **Interfaz Administrativa**  
✅ **API RESTful**  

## Tecnologías Utilizadas  

- **Backend**: Spring boot JAVA
- **Base de Datos**: SQL Postgresql 
- **Autenticación**: JWT (JSON Web Tokens)  
- **Frontend**: React.js PWA
- **Otros**: Bcrypt (hashing) 

## Instalación  

1. **Clonar el repositorio**:  
   ```sh
   git clone https://github.com/rubnhiguras/RhasUserManagement.git
   cd RhasUserManagement

2. **Instalar WSL o Linux con Docker**:
   Al instalar WSL o disponer de una máquina unix/linux, ejecutar los siguientes comandos:
   ```sudo apt update && sudo apt upgrade -y
   sudo apt install -y docker.io 
   sudo usermod -aG docker $USER
   sudo systemctl enable docker

4. **Desplegar**:
    ```
       .\boot.dev.sh
       .\reset.dev.sh 
      
   
