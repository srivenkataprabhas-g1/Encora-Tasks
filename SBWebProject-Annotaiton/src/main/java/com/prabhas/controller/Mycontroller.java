package com.prabhas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prabhas.model.Employee;

@RestController
public class Mycontroller {
    @Autowired
    private Employee employee;
    @GetMapping("/")
    public String display() {
        return "<marquee><h1>Welcome "+employee.getName()+"</h1></marquee>"
        		+ "<table style=\"border:'2px solid black';\"><tr>"+"<th>ID:</th><td>" + employee.getId() + "</td></tr><tr><th>Name:</th><td>" + employee.getName() + "</td></tr><tr><th>City:</th><td>" + employee.getCity()+"</td></tr></table>";
    }
}