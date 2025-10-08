package com.prabhas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prabhas.model.Employee;
import com.prabhas.repository.EmployeeRepository;

@Service
public class EmployeeService {
	private final EmployeeRepository repo;
	
	public EmployeeService(EmployeeRepository repo) {
		this.repo=repo;
	}
	
	public List<Employee> getAllEmployees(){
		return repo.findAll();
	}
	public Employee getEmployeeById(int id) {
		return repo.findById(id).orElse(null);
	}
	public Employee saveEmployee(Employee employee) {
		return repo.save(employee);
	}
	public Employee updateEmployee(int id,Employee employee) {
		// TODO Auto-generated method stub
		Employee existing=repo.findById(id).orElse(null);
		if(existing!=null) {
			existing.setName(employee.getName());
			existing.setCity(employee.getCity());
			repo.save(existing);
			return existing;
		}
		return null;
	}
	public void deleteEmployee(int id) {
		repo.deleteById(id);
	}

	public Employee patchEmployee(int id, Employee partialEmployee) {
	    Employee existing = repo.findById(id).orElse(null);
	    if (existing != null) {
	        if (partialEmployee.getName() != null) {
	            existing.setName(partialEmployee.getName());
	        }
	        if (partialEmployee.getCity() != null) {
	            existing.setCity(partialEmployee.getCity());
	        }
	        return repo.save(existing);
	    }
	    return null;
	}
	 public List<Employee> findByIdAndName(int id,String name){
		return repo.findByIdAndName(id,name);  
	  }
	  
	  public List<Employee> findByNameAndCity(String name,String city){
			return repo.findByNameandCity(name,city);  
	  }
}