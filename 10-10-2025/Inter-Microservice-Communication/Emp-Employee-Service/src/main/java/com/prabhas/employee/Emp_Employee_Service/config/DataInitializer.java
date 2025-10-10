package com.prabhas.employee.Emp_Employee_Service.config;

import com.prabhas.employee.Emp_Employee_Service.model.Employee;
import com.prabhas.employee.Emp_Employee_Service.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Initialize sample employees
        if (employeeRepository.count() == 0) {
            Employee emp1 = new Employee();
            emp1.setName("Guda Sri Venkata Prabhas");
            emp1.setEmail("prabhas@encora.com");
            emp1.setDepartment("Engineering");
            emp1.setSalary(75000.0);
            
            Employee emp2 = new Employee();
            emp2.setName("John Doe");
            emp2.setEmail("john.doe@encora.com");
            emp2.setDepartment("Sales");
            emp2.setSalary(60000.0);
            
            Employee emp3 = new Employee();
            emp3.setName("Jane Smith");
            emp3.setEmail("jane.smith@encora.com");
            emp3.setDepartment("Engineering");
            emp3.setSalary(80000.0);
            
            employeeRepository.save(emp1);
            employeeRepository.save(emp2);
            employeeRepository.save(emp3);
            
            System.out.println("Sample employees initialized!");
        }
    }
}