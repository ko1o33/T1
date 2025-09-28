package com.example.client_processing.service.impl;

import com.example.client_processing.entite.user.User;
import com.example.client_processing.repository.BlackListUserRepository;
import com.example.client_processing.repository.UserRepository;
import com.example.client_processing.service.UserService;
import com.example.client_processing.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BlackListUserRepository blackListRepository;

    public boolean checkUser(User user) {
        if(userRepository.existsByLogin(user.getLogin())) {
            throw new UserException("Данный логин занят");
        }
        if(userRepository.existsByEmail(user.getEmail())) {
            throw new UserException("Данный email занят");
        }
        if(blackListRepository.existsByLogin(user.getLogin())) {
            throw new UserException("Данный user заблокирован");
        }
        return true;
    }

    public User findByLogin(String login,String password) {
        var user = userRepository.findByLoginAndPassword(login,password);
        if(user.isEmpty()) {
            throw new UserException("Данный user не найден");
        }else if(blackListRepository.existsByLogin(login)) {
            throw new UserException("Данный user заблокирован");
        }else {
            return user.get();
        }
    }

    @Transactional
    public boolean userSave(User user) {
        if(userRepository.save(user).getId() != null) {
            return true;
        }
        return true;
    }

}
