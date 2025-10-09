package com.prabhas.config;

import com.prabhas.models.entity.*;
import com.prabhas.service.RoleService;
import com.prabhas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired 
    private UserService userService;
    
    @Autowired 
    private RoleService roleService;
    
    @Override
    public void run(String... args) {
        // Create roles if they don't exist
        createRoleIfNotExists("ROLE_USER", "Standard user role");
        createRoleIfNotExists("ROLE_ADMIN", "Administrator role");
        createRoleIfNotExists("ROLE_MANAGER", "Manager role");
        
        // Create default admin user if no users exist
        if (userService.getAllUsers().isEmpty()) {
            Set<String> adminRoles = new HashSet<>();
            adminRoles.add("ROLE_USER");
            adminRoles.add("ROLE_ADMIN");
            adminRoles.add("ROLE_MANAGER");
            
            userService.createUser(
                "admin", 
                "password", 
                "prabhasg03@gmail.com",
                "Guda", 
                "Sri Venkata Prabhas", 
                "9346819125", 
                adminRoles
            );
            
            // Create a regular user
            Set<String> userRoles = new HashSet<>();
            userRoles.add("ROLE_USER");
            
            userService.createUser(
                "user", 
                "password", 
                "user@example.com",
                "John", 
                "Doe", 
                "1234567890", 
                userRoles
            );
            
            System.out.println("Default users created successfully!");
        }
    }
    
    private void createRoleIfNotExists(String roleName, String description) {
        if (!roleService.existsByRoleName(roleName)) {
            roleService.createRole(roleName, description);
            System.out.println("Role created: " + roleName);
        }
    }
}