package com.chubb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @GetMapping("/order")            
    public String getOrder() {
        return "hello";
    }

    @PostMapping("/order")           
    public String saveOrder() {
        return "hello";
    }
}
