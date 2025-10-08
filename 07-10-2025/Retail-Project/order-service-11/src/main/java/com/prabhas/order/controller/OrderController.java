package com.prabhas.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${order.topic}")
    private String orderTopic;

    @Value("${inventory.topic}")
    private String inventoryTopic;

    // Receive new orders from clients and send to order topic
    @PostMapping
    public String createOrder(@RequestBody String orderJson) {
        kafkaTemplate.send(orderTopic, orderJson);
        return "Order placed and sent to order topic";
    }

    // Listen to inventory topic messages (simulating inventory update confirmation)
    @KafkaListener(topics = "${inventory.topic}", groupId = "order-group")
    public void listenInventoryTopic(String message) {
        System.out.println("Received message from inventory topic: " + message);
        // Business logic for processing inventory response can go here
    }
}
