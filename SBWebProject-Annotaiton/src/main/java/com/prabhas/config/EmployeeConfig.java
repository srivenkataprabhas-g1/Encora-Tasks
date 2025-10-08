package com.prabhas.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.prabhas.model.Employee;

@Configuration
public class EmployeeConfig {
	@Bean
	public CommandLineRunner cmdRunner() {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				// TODO Auto-generated method stub
				employee().display();
			}
		};
	}
	@Bean
	public Employee employee(){
		Employee emp=new Employee();
		emp.setId(16796);
		emp.setName("Guda Sri Venkata Prabhas");
		emp.setCity("Hyderabad");
		return emp;
	}
}
