package com.prabhas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prabhas.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

	  @Query("SELECT e FROM Employee e WHERE e.id = :id AND e.name LIKE %:name%")
	  public List<Employee> findByIdAndName(@Param("id") int id, @Param("name") String name);
	  
	  @Query("SELECT e FROM Employee e WHERE e.name LIKE %:name% AND e.city LIKE %:city%")
	  public List<Employee> findByNameandCity(@Param("name") String name,@Param("city") String city);
	   
}
