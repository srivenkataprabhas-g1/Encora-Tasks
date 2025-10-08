package com.prabhas.service;

import com.prabhas.model.Employee;
import com.prabhas.model.Department;
import com.prabhas.model.Passport;
import com.prabhas.repository.EmployeeRepository;
import com.prabhas.repository.DepartmentRepository;
import com.prabhas.repository.PassportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Autowired
    private PassportRepository passportRepository;
    
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }
    
    public Optional<Employee> getEmployeeWithDetails(Long id) {
        return employeeRepository.findByIdWithDetails(id);
    }
    
    public Employee createEmployee(Employee employee) {
        // Validate and set department if provided
        if (employee.getDepartment() != null && employee.getDepartment().getId() != null) {
            Department department = departmentRepository.findById(employee.getDepartment().getId())
                .orElseThrow(() -> new RuntimeException("Department not found"));
            employee.setDepartment(department);
        }
        
        // Validate passport number uniqueness if provided
        if (employee.getPassport() != null && employee.getPassport().getNumber() != null) {
            if (passportRepository.existsByNumber(employee.getPassport().getNumber())) {
                throw new RuntimeException("Passport number already exists");
            }
        }
        
        return employeeRepository.save(employee);
    }
    
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        
        employee.setName(employeeDetails.getName());
        employee.setCity(employeeDetails.getCity());
        
        // Update department if provided
        if (employeeDetails.getDepartment() != null && employeeDetails.getDepartment().getId() != null) {
            Department department = departmentRepository.findById(employeeDetails.getDepartment().getId())
                .orElseThrow(() -> new RuntimeException("Department not found"));
            employee.setDepartment(department);
        }
        
        return employeeRepository.save(employee);
    }
    
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        employeeRepository.delete(employee);
    }
    
    public List<Employee> getEmployeesByDepartment(Long departmentId) {
        return employeeRepository.findByDepartmentId(departmentId);
    }
    
    public List<Employee> getEmployeesByCity(String city) {
        return employeeRepository.findByCity(city);
    }
}
