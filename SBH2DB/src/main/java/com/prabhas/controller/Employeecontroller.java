package com.prabhas.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prabhas.model.Employee;
import com.prabhas.repository.EmployeeRepository;

@RestController
@RequestMapping("/Employee")
public class Employeecontroller {
	private final EmployeeRepository repository;
	public Employeecontroller(EmployeeRepository repository) {
		this.repository=repository;
	}
	@GetMapping
	public List<Employee>getAll(){
		return repository.findAll();
	}
	@PostMapping
	public Employee save(@RequestBody Employee employee) {
		return repository.save(employee);
	}
}
