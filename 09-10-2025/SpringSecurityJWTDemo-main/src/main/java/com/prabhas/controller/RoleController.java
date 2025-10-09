package com.prabhas.controller;

import com.prabhas.models.entity.Role;
import com.prabhas.models.entity.User;
import com.prabhas.service.RoleService;
import com.prabhas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private UserService userService;
    
    // Accessible by any authenticated user (USER or ADMIN)
    @GetMapping("/profile")
    public String userProfile() {
        return "Access granted to USER or ADMIN";
    }
    
    // Only users with ROLE_USER may call this
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/data")
    public String userData() {
        return "Sensitive data for ROLE_USER";
    }
    
    // Only users with ROLE_ADMIN may call this
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/data")
    public String adminData() {
        return "Sensitive data for ROLE_ADMIN";
    }
    
    // Get all roles - ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }
    
    // Create new role - ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Role> createRole(@RequestParam String roleName, 
                                         @RequestParam(required = false) String description) {
        Role role = roleService.createRole(roleName, description);
        return ResponseEntity.ok(role);
    }
    
    // Update role - ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{roleId}")
    public ResponseEntity<Role> updateRole(@PathVariable Long roleId, 
                                         @RequestParam String description) {
        Role role = roleService.updateRole(roleId, description);
        return ResponseEntity.ok(role);
    }
    
    // Delete role - ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{roleName}")
    public ResponseEntity<String> deleteRole(@PathVariable String roleName) {
        roleService.deleteRole(roleName);
        return ResponseEntity.ok("Role '" + roleName + "' deleted successfully");
    }
    
    // Add role to user - ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/user/{username}/add-role")
    public ResponseEntity<User> addRoleToUser(@PathVariable String username, 
                                            @RequestParam String roleName) {
        User user = userService.addRoleToUser(username, roleName);
        return ResponseEntity.ok(user);
    }
    
    // Remove role from user - ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/user/{username}/remove-role")
    public ResponseEntity<User> removeRoleFromUser(@PathVariable String username, 
                                                 @RequestParam String roleName) {
        User user = userService.removeRoleFromUser(username, roleName);
        return ResponseEntity.ok(user);
    }
    
    // Get users by role - ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{roleName}/users")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String roleName) {
        List<User> users = userService.getUsersByRole(roleName);
        return ResponseEntity.ok(users);
    }
}