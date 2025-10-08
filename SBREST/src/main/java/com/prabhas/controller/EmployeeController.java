package com.prabhas.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.prabhas.model.Employee;
import com.prabhas.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Employee createEmployee(@RequestBody Employee employee) {
        return service.saveEmployee(employee);
    }

    @GetMapping("/all")
    public List<Employee> getAllEmployees() {
        return service.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable int id) {
        return service.getEmployeeById(id);
    }

    @PutMapping("/update/{id}")
    public Employee updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
        return service.updateEmployee(id, employee);
    }

    @PatchMapping("/patch/{id}")
    public Employee patchEmployee(@PathVariable int id, @RequestBody Employee partialEmployee) {
        return service.patchEmployee(id, partialEmployee);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable int id) {
        service.deleteEmployee(id);
        return "Employee with ID: " + id + " deleted successfully";
    }
    @GetMapping("/id/{id}/name/{name}")
    public List<Employee> findByIdandName(@PathVariable int id,@PathVariable String name){
    	return service.findByIdAndName(id,name);
    }
    @GetMapping("/name/{name}/city/{city}")
    public List<Employee> findByNameandCity(@PathVariable String name,@PathVariable String city){
    	return service.findByNameAndCity(name,city);
    }
}