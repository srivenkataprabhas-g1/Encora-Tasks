package com.prabhas.controller;

import com.prabhas.model.Tasks;
import com.prabhas.service.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TasksController {
    
    @Autowired
    private TasksService tasksService;
    
    @GetMapping("/all")
    public ResponseEntity<List<Tasks>> getAllTasks() {
        return ResponseEntity.ok(tasksService.getAllTasks());
    }
    
    @GetMapping("/get/task/{id}")
    public ResponseEntity<Tasks> getTaskById(@PathVariable Long id) {
        return tasksService.getTaskById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/get/employees/{id}")
    public ResponseEntity<Tasks> getTaskWithEmployees(@PathVariable Long id) {
        return tasksService.getTaskWithEmployees(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/create")
    public ResponseEntity<Tasks> createTask(@RequestBody Tasks task) {
        Tasks createdTask = tasksService.createTask(task);
        return ResponseEntity.ok(createdTask);
    }
    
    @PutMapping("/put/{id}")
    public ResponseEntity<Tasks> updateTask(@PathVariable Long id, @RequestBody Tasks task) {
        try {
            Tasks updatedTask = tasksService.updateTask(id, task);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        try {
            tasksService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/task/{taskId}/employees/{employeeId}")
    public ResponseEntity<Tasks> assignEmployeeToTask(@PathVariable Long taskId, @PathVariable Long employeeId) {
        try {
            Tasks task = tasksService.assignEmployeeToTask(taskId, employeeId);
            return ResponseEntity.ok(task);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/task/{taskId}/employees/{employeeId}")
    public ResponseEntity<Tasks> removeEmployeeFromTask(@PathVariable Long taskId, @PathVariable Long employeeId) {
        try {
            Tasks task = tasksService.removeEmployeeFromTask(taskId, employeeId);
            return ResponseEntity.ok(task);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Tasks>> getTasksByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(tasksService.getTasksByEmployee(employeeId));
    }
}
