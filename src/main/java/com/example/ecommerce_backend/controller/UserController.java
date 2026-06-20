package com.example.ecommerce_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/api/user/profile")
    public String getProfile() {
        return "This is a protected endpoint. Only logged-in users can see this.";
    }
}
