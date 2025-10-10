package com.prabhas.Emp_Order_Service.controller;

import com.prabhas.Emp_Order_Service.model.Order;
import com.prabhas.Emp_Order_Service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Get all orders
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // Original method enhanced with database
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get orders by employee ID (for inter-service communication)
    @GetMapping("/employee/{employeeId}")
    public List<Order> getOrdersByEmployeeId(@PathVariable Long employeeId) {
        return orderService.getOrdersByEmployeeId(employeeId);
    }

    // Create new order
    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    // Update order
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        try {
            Order updatedOrder = orderService.updateOrder(id, order);
            return ResponseEntity.ok(updatedOrder);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete order
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    // Inter-service communication: Get order with employee details
    @GetMapping("/{id}/with-employee")
    public ResponseEntity<Object> getOrderWithEmployeeDetails(@PathVariable Long id) {
        Object orderWithEmployee = orderService.getOrderWithEmployeeDetails(id);
        return orderWithEmployee != null ? ResponseEntity.ok(orderWithEmployee) : ResponseEntity.notFound().build();
    }
}