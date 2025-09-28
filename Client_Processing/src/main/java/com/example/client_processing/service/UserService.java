package com.example.client_processing.service;

import com.example.client_processing.dto.UserRequest;
import com.example.client_processing.entite.user.User;
import com.example.client_processing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean checkUserUser(User user) {

        return true
    }

    @Transactional
    public void userSava(User user) {
        userRepository.save(user);
    }

}
