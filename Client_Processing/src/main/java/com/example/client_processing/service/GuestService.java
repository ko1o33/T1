package com.example.client_processing.service;

import com.example.client_processing.dto.Jwt.JwtRequest;
import org.springframework.http.ResponseEntity;

public interface GuestService {
    ResponseEntity<?> UserToken(JwtRequest jwtRequest);
}
