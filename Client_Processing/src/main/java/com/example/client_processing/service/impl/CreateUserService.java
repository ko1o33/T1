package com.example.client_processing.service.impl;

import com.example.client_processing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CreateUserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return User.builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .roles(user.getRole().toString())
                .build();
    }

}
