package com.prabhas.service;

import com.prabhas.model.Passport;
import com.prabhas.model.Employee;
import com.prabhas.repository.PassportRepository;
import com.prabhas.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PassportService {
    
    @Autowired
    private PassportRepository passportRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    public List<Passport> getAllPassports() {
        return passportRepository.findAll();
    }
    
    public Optional<Passport> getPassportById(Long id) {
        return passportRepository.findById(id);
    }
    
    public Optional<Passport> getPassportByNumber(String number) {
        return passportRepository.findByNumber(number);
    }
    
    public Passport createPassport(Passport passport) {
        // Check if passport number already exists
        if (passportRepository.existsByNumber(passport.getNumber())) {
            throw new RuntimeException("Passport number '" + passport.getNumber() + "' already exists");
        }
        return passportRepository.save(passport);
    }
    
    public Passport updatePassport(Long id, Passport passportDetails) {
        Passport passport = passportRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Passport not found with id: " + id));
        
        // Check if new passport number already exists (and it's not the same passport)
        if (!passport.getNumber().equals(passportDetails.getNumber()) && 
            passportRepository.existsByNumber(passportDetails.getNumber())) {
            throw new RuntimeException("Passport number '" + passportDetails.getNumber() + "' already exists");
        }
        
        passport.setNumber(passportDetails.getNumber());
        return passportRepository.save(passport);
    }
    
    public void deletePassport(Long id) {
        Passport passport = passportRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Passport not found with id: " + id));
        
        // Check if passport is assigned to an employee
        if (passport.getEmployee() != null) {
            throw new RuntimeException("Cannot delete passport that is assigned to an employee");
        }
        
        passportRepository.delete(passport);
    }
    
    public Passport assignPassportToEmployee(Long passportId, Long employeeId) {
        Passport passport = passportRepository.findById(passportId)
            .orElseThrow(() -> new RuntimeException("Passport not found"));
        
        Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new RuntimeException("Employee not found"));
        
        // Check if employee already has a passport
        if (employee.getPassport() != null) {
            throw new RuntimeException("Employee already has a passport assigned");
        }
        
        // Check if passport is already assigned to another employee
        if (passport.getEmployee() != null) {
            throw new RuntimeException("Passport is already assigned to another employee");
        }
        
        // Set bidirectional relationship
        passport.setEmployee(employee);
        employee.setPassport(passport);
        
        employeeRepository.save(employee);
        return passportRepository.save(passport);
    }
    
    public Passport unassignPassportFromEmployee(Long passportId) {
        Passport passport = passportRepository.findById(passportId)
            .orElseThrow(() -> new RuntimeException("Passport not found"));
        
        if (passport.getEmployee() != null) {
            Employee employee = passport.getEmployee();
            
            // Remove bidirectional relationship
            passport.setEmployee(null);
            employee.setPassport(null);
            
            employeeRepository.save(employee);
        }
        
        return passportRepository.save(passport);
    }
    
    public boolean isPassportNumberExists(String number) {
        return passportRepository.existsByNumber(number);
    }
}