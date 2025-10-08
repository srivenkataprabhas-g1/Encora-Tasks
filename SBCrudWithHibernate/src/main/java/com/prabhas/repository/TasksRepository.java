package com.prabhas.repository;

import com.prabhas.model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TasksRepository extends JpaRepository<Tasks, Long> {
    
    List<Tasks> findByDescriptionContaining(String keyword);
    
    @Query("SELECT t FROM Tasks t LEFT JOIN FETCH t.employees WHERE t.id = :id")
    Optional<Tasks> findByIdWithEmployees(@Param("id") Long id);
    
    @Query("SELECT t FROM Tasks t JOIN t.employees e WHERE e.id = :employeeId")
    List<Tasks> findTasksByEmployeeId(@Param("employeeId") Long employeeId);
}
