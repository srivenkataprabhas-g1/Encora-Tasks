package com.prabhas.Emp_Order_Service.config;

import com.prabhas.Emp_Order_Service.model.Order;
import com.prabhas.Emp_Order_Service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Initialize sample orders
        if (orderRepository.count() == 0) {
            Order order1 = new Order();
            order1.setName("Laptop");
            order1.setPrice(20000.0);
            order1.setQuantity(1);
            order1.setEmployeeId(1L);
            order1.setOrderStatus("COMPLETED");
            order1.calculateTotal();
            
            Order order2 = new Order();
            order2.setName("Mouse");
            order2.setPrice(500.0);
            order2.setQuantity(2);
            order2.setEmployeeId(1L);
            order2.setOrderStatus("PENDING");
            order2.calculateTotal();
            
            Order order3 = new Order();
            order3.setName("Keyboard");
            order3.setPrice(1500.0);
            order3.setQuantity(1);
            order3.setEmployeeId(2L);
            order3.setOrderStatus("SHIPPED");
            order3.calculateTotal();
            
            orderRepository.save(order1);
            orderRepository.save(order2);
            orderRepository.save(order3);
            
            System.out.println("Sample orders initialized!");
        }
    }
}