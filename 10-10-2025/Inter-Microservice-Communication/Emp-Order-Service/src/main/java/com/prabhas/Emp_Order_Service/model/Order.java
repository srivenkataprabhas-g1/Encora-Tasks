package com.prabhas.Emp_Order_Service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private Double price;
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Column(name = "employee_id")
    private Long employeeId;
    
    @Column(name = "order_status")
    private String orderStatus = "PENDING";
    
    @Column(name = "total_amount")
    private Double totalAmount;
    
    // Calculate total amount
    public void calculateTotal() {
        this.totalAmount = this.price * this.quantity;
    }
}