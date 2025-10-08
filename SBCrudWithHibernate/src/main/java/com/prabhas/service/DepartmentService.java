package com.prabhas.service;

import com.prabhas.model.Department;
import com.prabhas.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DepartmentService {
    
    @Autowired
    private DepartmentRepository departmentRepository;
    
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
    
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }
    
    public Optional<Department> getDepartmentWithEmployees(Long id) {
        return departmentRepository.findByIdWithEmployees(id);
    }
    
    public Department createDepartment(Department department) {
        // Check if department name already exists
        Optional<Department> existing = departmentRepository.findByName(department.getName());
        if (existing.isPresent()) {
            throw new RuntimeException("Department with name '" + department.getName() + "' already exists");
        }
        return departmentRepository.save(department);
    }
    
    public Department updateDepartment(Long id, Department departmentDetails) {
        Department department = departmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
        
        department.setName(departmentDetails.getName());
        return departmentRepository.save(department);
    }
    
    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
        
        // Check if department has employees
        if (!department.getEmployees().isEmpty()) {
            throw new RuntimeException("Cannot delete department with employees");
        }
        
        departmentRepository.delete(department);
    }
}