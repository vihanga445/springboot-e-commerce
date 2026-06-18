package com.example.ecommerce_backend.controller;

import com.example.ecommerce_backend.dto.LoginRequest;
import com.example.ecommerce_backend.dto.UserDto;
import com.example.ecommerce_backend.entity.User;
import com.example.ecommerce_backend.security.JwtUtils;
import com.example.ecommerce_backend.service.UserService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import com.example.ecommerce_backend.security.JwtUtils;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;

    }


    @PostMapping("/save")
    public ResponseEntity<User> saveUser(@RequestBody UserDto userDto) {
        User savedUser = userService.saveUser(userDto);
        return ResponseEntity.ok(savedUser);
    }


    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}


