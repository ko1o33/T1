package com.example.client_processing.service.impl;

import com.example.client_processing.entite.user.User;
import com.example.client_processing.repository.BlackListUserRepository;
import com.example.client_processing.repository.UserRepository;
import com.example.client_processing.service.UserService;
import com.example.client_processing.exception.UserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BlackListUserRepository blackListRepository;

    @Transactional
    public void userSave(User user) {
        if (userRepository.existsByLogin(user.getLogin())) {
            log.info("This login {} is busy", user.getLogin());
            throw new UserException("Данный логин занят");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            log.info("This email {} is busy", user.getEmail());
            throw new UserException("Данный email занят");
        }
        if (blackListRepository.existsByLogin(user.getLogin())) {
            log.info("This user {} is block", user.getEmail());
            throw new UserException("Данный user заблокирован");
        }
        userRepository.save(user);
        log.info("User {} saved", user.getLogin());
    }

    public User findByLogin(String login, String password) {
        log.info("Аутентификация пользователя");
        var user = userRepository.findByLoginAndPassword(login, password);
        if (user.isEmpty()) {
            throw new UserException("Данный user не найден");
        } else if (blackListRepository.existsByLogin(login)) {
            log.info("Пользователь забанен");
            throw new UserException("Данный user заблокирован");
        } else {
            log.info("Пользователь аутентифицирован");
            return user.get();
        }
    }

}
