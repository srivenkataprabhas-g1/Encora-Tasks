package com.prabhas.model.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/user")
    public String userAccess() {
        return "Hello User! You are authenticated!!";
    }

    @GetMapping("/admin")
    public String adminAccess() {
        return "Hello Admin! You are authenticated!!";
    }
}