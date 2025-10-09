package com.prabhas.service;

import com.prabhas.models.entity.Role;
import com.prabhas.models.entity.User;
import com.prabhas.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public User createUser(String username, String password, String email, 
                          String firstName, String lastName, String phone, Set<String> roleNames) {
        
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists: " + username);
        }
        
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists: " + email);
        }
        
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        user.setEnabled(true);
        
        // Add roles
        if (roleNames != null && !roleNames.isEmpty()) {
            Set<Role> roles = roleService.findRolesByNames(roleNames);
            user.setRoles(roles);
        }
        
        return userRepository.save(user);
    }
    
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public Optional<User> findByUsernameWithRoles(String username) {
        return userRepository.findByUsernameWithRoles(username);
    }
    
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public User addRoleToUser(String username, String roleName) {
        User user = getUserByUsername(username);
        Role role = roleService.getRoleByName(roleName);
        
        user.addRole(role);
        return userRepository.save(user);
    }
    
    public User removeRoleFromUser(String username, String roleName) {
        User user = getUserByUsername(username);
        Role role = roleService.getRoleByName(roleName);
        
        user.removeRole(role);
        return userRepository.save(user);
    }
    
    public User updateUser(String username, String email, String firstName, 
                          String lastName, String phone) {
        User user = getUserByUsername(username);
        
        if (email != null && !email.equals(user.getEmail())) {
            if (userRepository.existsByEmail(email)) {
                throw new RuntimeException("Email already exists: " + email);
            }
            user.setEmail(email);
        }
        
        if (firstName != null) user.setFirstName(firstName);
        if (lastName != null) user.setLastName(lastName);
        if (phone != null) user.setPhone(phone);
        
        return userRepository.save(user);
    }
    
    public void deleteUser(String username) {
        User user = getUserByUsername(username);
        
        // Remove user from all roles
        user.getRoles().clear();
        
        userRepository.delete(user);
    }
    
    public List<User> getUsersByRole(String roleName) {
        return userRepository.findUsersByRoleName(roleName);
    }
    
    public User changePassword(String username, String newPassword) {
        User user = getUserByUsername(username);
        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }
}