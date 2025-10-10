package com.prabhas.employee.Emp_Employee_Service.controller;

import com.prabhas.employee.Emp_Employee_Service.model.Employee;
import com.prabhas.employee.Emp_Employee_Service.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private RestTemplate restTemplate;

    // Get all employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    // Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create new employee
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    // Update employee
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        try {
            Employee updatedEmployee = employeeService.updateEmployee(id, employee);
            return ResponseEntity.ok(updatedEmployee);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete employee
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    // Inter-service communication: Get employee with their orders
    @GetMapping("/{id}/with-orders")
    public ResponseEntity<Employee> getEmployeeWithOrders(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeWithOrders(id);
        return employee != null ? ResponseEntity.ok(employee) : ResponseEntity.notFound().build();
    }

    // Original inter-service method (enhanced)
    @GetMapping("/getorderid/{id}")
    public Map<String, Object> getOrderDetails(@PathVariable("id") Long id) {
        String url = "http://localhost:8081/order/" + id;
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response == null) {
                response = new HashMap<>();
            }
            response.put("Message", "Order Fetched Successfully");
            response.put("FetchedBy", "Employee Service");
            return response;
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("Error", "Failed to fetch order: " + e.getMessage());
            errorResponse.put("OrderId", id);
            return errorResponse;
        }
    }

    @GetMapping("/info")
    public String getOrderInfo() {
        return "Hello Guda Sri Venkata Prabhas - Employee Service with Database Integration";
    }
}