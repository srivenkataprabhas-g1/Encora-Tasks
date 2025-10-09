package com.prabhas.service;

import com.prabhas.models.entity.Role;
import com.prabhas.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

@Service
@Transactional
public class RoleService {
    
    @Autowired
    private RoleRepository roleRepository;
    
    public Role createRole(String roleName, String description) {
        if (roleRepository.existsByRoleName(roleName)) {
            throw new RuntimeException("Role already exists: " + roleName);
        }
        
        Role role = new Role(roleName, description);
        return roleRepository.save(role);
    }
    
    public Optional<Role> findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }
    
    public Role getRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
    }
    
    public Set<Role> findRolesByNames(Set<String> roleNames) {
        List<Role> roles = roleRepository.findByRoleNameIn(roleNames);
        return new HashSet<>(roles);
    }
    
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
    
    public Role updateRole(Long roleId, String newDescription) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));
        
        role.setDescription(newDescription);
        return roleRepository.save(role);
    }
    
    public void deleteRole(String roleName) {
        Role role = getRoleByName(roleName);
        roleRepository.delete(role);
    }
    
    public boolean existsByRoleName(String roleName) {
        return roleRepository.existsByRoleName(roleName);
    }
}