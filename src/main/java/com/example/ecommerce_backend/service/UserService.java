package com.example.ecommerce_backend.service;

import com.example.ecommerce_backend.dto.UserDto;
import com.example.ecommerce_backend.entity.User;

import java.util.List;

public interface UserService {



    User saveUser(UserDto userDto);

    List<User> getAllUsers();

}
