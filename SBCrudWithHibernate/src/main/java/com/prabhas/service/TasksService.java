package com.prabhas.service;

import com.prabhas.model.Tasks;
import com.prabhas.model.Employee;
import com.prabhas.repository.TasksRepository;
import com.prabhas.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TasksService {
    
    @Autowired
    private TasksRepository tasksRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    public List<Tasks> getAllTasks() {
        return tasksRepository.findAll();
    }
    
    public Optional<Tasks> getTaskById(Long id) {
        return tasksRepository.findById(id);
    }
    
    public Optional<Tasks> getTaskWithEmployees(Long id) {
        return tasksRepository.findByIdWithEmployees(id);
    }
    
    public Tasks createTask(Tasks task) {
        return tasksRepository.save(task);
    }
    
    public Tasks updateTask(Long id, Tasks taskDetails) {
        Tasks task = tasksRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        
        task.setDescription(taskDetails.getDescription());
        return tasksRepository.save(task);
    }
    
    public void deleteTask(Long id) {
        Tasks task = tasksRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        tasksRepository.delete(task);
    }
    
    public Tasks assignEmployeeToTask(Long taskId, Long employeeId) {
        Tasks task = tasksRepository.findById(taskId)
            .orElseThrow(() -> new RuntimeException("Task not found"));
        
        Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new RuntimeException("Employee not found"));
        
        task.getEmployees().add(employee);
        employee.getTasks().add(task);
        
        return tasksRepository.save(task);
    }
    
    public Tasks removeEmployeeFromTask(Long taskId, Long employeeId) {
        Tasks task = tasksRepository.findById(taskId)
            .orElseThrow(() -> new RuntimeException("Task not found"));
        
        Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new RuntimeException("Employee not found"));
        
        task.getEmployees().remove(employee);
        employee.getTasks().remove(task);
        
        return tasksRepository.save(task);
    }
    
    public List<Tasks> getTasksByEmployee(Long employeeId) {
        return tasksRepository.findTasksByEmployeeId(employeeId);
    }
}
