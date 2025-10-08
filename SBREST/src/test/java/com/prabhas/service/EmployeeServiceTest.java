package com.prabhas.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.prabhas.model.Employee;
import com.prabhas.repository.EmployeeRepository;

class EmployeeServiceTest {

    @Mock
    private EmployeeRepository empRepository;

    @InjectMocks
    private EmployeeService employeeservice;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() throws Exception {
        // Initialize mocks and inject them
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        // Release mock resources
        closeable.close();
    }

    @Test
    void testGetEmployeeById() {
        Employee emp = new Employee(16796, "Guda Sri Venkata Prabhas", "Hyderabad");
        when(empRepository.findById(16796)).thenReturn(Optional.of(emp));

        Employee res = employeeservice.getEmployeeById(16796);
        assertThat(res.getName()).isEqualTo("Guda Sri Venkata Prabhas");
        verify(empRepository, times(1)).findById(16796);
    }

    @Test
    void testSaveEmployee() {
        Employee emp = new Employee(12345, "John Doe", "New York");
        when(empRepository.save(emp)).thenReturn(emp);

        Employee savedEmp = employeeservice.saveEmployee(emp);
        assertThat(savedEmp).isNotNull();
        assertThat(savedEmp.getName()).isEqualTo("John Doe");
        verify(empRepository, times(1)).save(emp);
    }

    @Test
    void testDeleteEmployee() {
        int empId = 12345;
        doNothing().when(empRepository).deleteById(empId);

        employeeservice.deleteEmployee(empId);
        verify(empRepository, times(1)).deleteById(empId);
    }
}