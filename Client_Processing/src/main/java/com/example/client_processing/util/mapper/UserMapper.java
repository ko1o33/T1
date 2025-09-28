package com.example.client_processing.util.mapper;

import com.example.client_processing.dto.UserRequest;
import com.example.client_processing.entite.user.User;
import org.apache.kafka.clients.ClientRequest;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User mapToUser(UserRequest userRequest) {
        var user = User.builder()
                .login(userRequest.getLogin())
                .password(userRequest.getPassword())
                .email(userRequest.getEmail())
                .build();
        return user;
    }
}
