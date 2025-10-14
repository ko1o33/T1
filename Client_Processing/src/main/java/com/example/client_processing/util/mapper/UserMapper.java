package com.example.client_processing.util.mapper;

import com.example.client_processing.dto.user.UserRequest;
import com.example.client_processing.entite.user.RoleList;
import com.example.client_processing.entite.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public User mapToUser(UserRequest userRequest) {
        var user = User.builder()
                .login(userRequest.getLogin())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .email(userRequest.getEmail())
                .role(RoleList.CURRENT_CLIENT)
                .build();
        return user;
    }
}
