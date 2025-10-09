package com.prabhas.controller;

import com.prabhas.dto.UserCreateRequest;
import com.prabhas.models.entity.User;
import com.prabhas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    // Get all users - ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    
    // Get user by username - ADMIN or the user themselves
    @PreAuthorize("hasRole('ADMIN') or authentication.name == #username")
    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }
    
    // Create new user - ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<User> createUser(
        @RequestBody UserCreateRequest request
    ) {
        User user = userService.createUser(
            request.getUsername(),
            request.getPassword(),
            request.getEmail(),
            request.getFirstName(),
            request.getLastName(),
            request.getPhone(),
            request.getRoleNames()
        );
        return ResponseEntity.ok(user);
    }
    
    // Update user - ADMIN or the user themselves
    @PreAuthorize("hasRole('ADMIN') or authentication.name == #username")
    @PutMapping("/{username}")
    public ResponseEntity<User> updateUser(@PathVariable String username,
                                         @RequestParam(required = false) String email,
                                         @RequestParam(required = false) String firstName,
                                         @RequestParam(required = false) String lastName,
                                         @RequestParam(required = false) String phone) {
        User user = userService.updateUser(username, email, firstName, lastName, phone);
        return ResponseEntity.ok(user);
    }
    
    // Delete user - ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok("User '" + username + "' deleted successfully");
    }
    
    // Change password - ADMIN or the user themselves
    @PreAuthorize("hasRole('ADMIN') or authentication.name == #username")
    @PutMapping("/{username}/change-password")
    public ResponseEntity<String> changePassword(@PathVariable String username,
                                               @RequestParam String newPassword) {
        userService.changePassword(username, newPassword);
        return ResponseEntity.ok("Password changed successfully");
    }
}