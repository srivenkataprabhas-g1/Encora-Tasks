package com.inv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class KafkaController {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${inventory.topic}")
    private String inventoryTopic;

    @PostMapping
    public String sendOrderToInventory(@RequestBody String orderDetails) {
        kafkaTemplate.send(inventoryTopic, orderDetails);
        return "Order sent to inventory topic";
    }
}