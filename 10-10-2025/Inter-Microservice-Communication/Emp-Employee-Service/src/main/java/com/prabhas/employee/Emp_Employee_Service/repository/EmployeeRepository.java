package com.prabhas.employee.Emp_Employee_Service.repository;

import com.prabhas.employee.Emp_Employee_Service.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    List<Employee> findByDepartment(String department);
    
    List<Employee> findByNameContainingIgnoreCase(String name);
    
    @Query("SELECT e FROM Employee e WHERE e.salary >= ?1")
    List<Employee> findBySalaryGreaterThanEqual(Double salary);
}