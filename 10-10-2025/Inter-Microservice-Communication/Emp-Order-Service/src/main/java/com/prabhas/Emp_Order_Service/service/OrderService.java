package com.prabhas.Emp_Order_Service.service;

import com.prabhas.Emp_Order_Service.model.Order;
import com.prabhas.Emp_Order_Service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private RestTemplate restTemplate;
    
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
    
    public List<Order> getOrdersByEmployeeId(Long employeeId) {
        return orderRepository.findByEmployeeId(employeeId);
    }
    
    public Order createOrder(Order order) {
        order.calculateTotal();
        return orderRepository.save(order);
    }
    
    public Order updateOrder(Long id, Order order) {
        order.setId(id);
        order.calculateTotal();
        return orderRepository.save(order);
    }
    
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
    
    // Inter-service communication method
    public Object getOrderWithEmployeeDetails(Long orderId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            try {
                // Call Employee Service to get employee details
                String employeeServiceUrl = "http://localhost:8083/employee/" + order.getEmployeeId();
                Object employeeDetails = restTemplate.getForObject(employeeServiceUrl, Object.class);
                
                // Create response with both order and employee details
                return new Object() {
                    public final Order order = orderOpt.get();
                    public final Object employee = employeeDetails;
                };
            } catch (Exception e) {
                return new Object() {
                    public final Order order = orderOpt.get();
                    public final String employee = "Unable to fetch employee details: " + e.getMessage();
                };
            }
        }
        return null;
    }
}