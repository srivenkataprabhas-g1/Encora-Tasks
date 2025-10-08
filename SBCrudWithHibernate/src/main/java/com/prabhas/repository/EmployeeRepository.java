package com.prabhas.repository;

import com.prabhas.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    List<Employee> findByName(String name);
    List<Employee> findByCity(String city);
    List<Employee> findByDepartmentId(Long departmentId);
    
    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.reviews WHERE e.id = :id")
    Optional<Employee> findByIdWithReviews(@Param("id") Long id);
    
    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.tasks WHERE e.id = :id")
    Optional<Employee> findByIdWithTasks(@Param("id") Long id);
    
    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.passport LEFT JOIN FETCH e.department WHERE e.id = :id")
    Optional<Employee> findByIdWithDetails(@Param("id") Long id);
}
