package com.prabhas.controller;

import com.prabhas.model.Passport;
import com.prabhas.service.PassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/passports")
@CrossOrigin(origins = "*")
public class PassportController {
    
    @Autowired
    private PassportService passportService;
    
    // Get all passports
    @GetMapping("/all")
    public ResponseEntity<List<Passport>> getAllPassports() {
        List<Passport> passports = passportService.getAllPassports();
        return ResponseEntity.ok(passports);
    }
    
    // Get passport by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<Passport> getPassportById(@PathVariable Long id) {
        return passportService.getPassportById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    // Get passport by number
    @GetMapping("/number/{number}")
    public ResponseEntity<Passport> getPassportByNumber(@PathVariable String number) {
        return passportService.getPassportByNumber(number)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    // Create new passport
    @PostMapping("/create")
    public ResponseEntity<?> createPassport(@RequestBody Passport passport) {
        try {
            Passport createdPassport = passportService.createPassport(passport);
            return ResponseEntity.ok(createdPassport);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        }
    }
    
    // Update passport
    @PutMapping("/put/{id}")
    public ResponseEntity<?> updatePassport(@PathVariable Long id, @RequestBody Passport passport) {
        try {
            Passport updatedPassport = passportService.updatePassport(id, passport);
            return ResponseEntity.ok(updatedPassport);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        }
    }
    
    // Delete passport
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePassport(@PathVariable Long id) {
        try {
            passportService.deletePassport(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        }
    }
    
    // Assign passport to employee
    @PostMapping("post/{passportId}/assign/{employeeId}")
    public ResponseEntity<?> assignPassportToEmployee(
            @PathVariable Long passportId, 
            @PathVariable Long employeeId) {
        try {
            Passport passport = passportService.assignPassportToEmployee(passportId, employeeId);
            return ResponseEntity.ok(passport);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        }
    }
    
    // Unassign passport from employee
    @PostMapping("post/{passportId}/unassign")
    public ResponseEntity<?> unassignPassportFromEmployee(@PathVariable Long passportId) {
        try {
            Passport passport = passportService.unassignPassportFromEmployee(passportId);
            return ResponseEntity.ok(passport);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        }
    }
    
    // Check if passport number exists
    @GetMapping("/exists/{number}")
    public ResponseEntity<Map<String, Boolean>> checkPassportNumberExists(@PathVariable String number) {
        boolean exists = passportService.isPassportNumberExists(number);
        return ResponseEntity.ok(Map.of("exists", exists));
    }
}
