package com.prabhas.employee.Emp_Employee_Service.service;

import com.prabhas.employee.Emp_Employee_Service.model.Employee;
import com.prabhas.employee.Emp_Employee_Service.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private RestTemplate restTemplate;
    
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }
    
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
    
    public Employee updateEmployee(Long id, Employee employee) {
        employee.setId(id);
        return employeeRepository.save(employee);
    }
    
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
    
    // Inter-service communication method
    public Employee getEmployeeWithOrders(Long employeeId) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            try {
                // Call Order Service to get employee's orders
                String orderServiceUrl = "http://localhost:8081/order/employee/" + employeeId;
                Object orders = restTemplate.getForObject(orderServiceUrl, Object.class);
                employee.setOrderDetails(orders);
            } catch (Exception e) {
                employee.setOrderDetails("Unable to fetch orders: " + e.getMessage());
            }
            return employee;
        }
        return null;
    }
}