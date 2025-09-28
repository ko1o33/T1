package com.example.client_processing.controller;

import com.example.client_processing.dto.UserRequest;
import com.example.client_processing.dto.UserResponse;
import com.example.client_processing.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserMapper userMapper;

    @PostMapping("/create")
    public UserResponse createUser(@RequestBody UserRequest userRequest) {
        //проверка
        var user = userMapper.mapToUser(userRequest);
        UserResponse userResponse = new UserResponse();
        return userResponse;
    }

}
