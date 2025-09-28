package com.example.client_processing.controller;

import com.example.client_processing.dto.UserRequest;
import com.example.client_processing.service.UserService;
import com.example.client_processing.exception.UserException;
import com.example.client_processing.util.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserMapper userMapper;

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest userRequest) {
        var user = userMapper.mapToUser(userRequest);
        try {
            userService.checkUser(user);
            userService.userSave(user);
            if(userService.userSave(user)){
                return ResponseEntity.ok("user создан");
            }
            throw new UserException("Произошла ошибка");
        }catch (UserException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

}
