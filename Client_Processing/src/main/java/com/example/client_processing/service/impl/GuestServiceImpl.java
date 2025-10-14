package com.example.client_processing.service.impl;

import com.example.client_processing.dto.Jwt.JwtRequest;
import com.example.client_processing.dto.Jwt.JwtResponse;
import com.example.client_processing.repository.UserRepository;
import com.example.client_processing.service.GuestService;
import com.example.client_processing.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
@Slf4j
public class GuestServiceImpl implements GuestService {

    private final JwtTokenUtils jwtTokenUtils;
    private final CreateUserService userService;

    @Override
    public ResponseEntity<?> UserToken(JwtRequest jwtRequest) {
        UserDetails newUser = userService.loadUserByUsername(jwtRequest.getUsername());
        String token = jwtTokenUtils.createToken(newUser);
        log.info("User : " + jwtRequest.getUsername() + "token : " + token);
        return ResponseEntity.ok(new JwtResponse(token));
    }

}
