package com.example.client_processing.service;

import com.example.client_processing.entite.user.User;

public interface UserService {

    boolean checkUser(User user);

    boolean userSave(User user);

    User findByLogin(String login,String password);

}
