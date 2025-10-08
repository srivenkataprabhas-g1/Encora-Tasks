package com.prabhas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prabhas.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Integer>{

}
