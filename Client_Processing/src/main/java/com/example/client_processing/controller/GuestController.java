package com.example.client_processing.controller;


import com.example.client_processing.dto.Jwt.JwtRequest;
import com.example.client_processing.exception.AppError;
import com.example.client_processing.service.GuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.annotation.HttpIncomeRequestLog;
import org.example.annotation.HttpOutcomeRequestLog;
import org.example.annotation.LogDatasourceError;
import org.example.annotation.Metric;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GuestController {

    private final GuestService guestService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/authentication")
    @LogDatasourceError
    @Metric
    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    public ResponseEntity<?> UserToken(@RequestBody JwtRequest jwtRequest) {
        try {
            log.info("User verification :" + jwtRequest.getUsername());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        } catch (BadCredentialsException e) {
            log.info("User's not verification :" + jwtRequest.getUsername());
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Некоректный логин или пароль"),
                    HttpStatus.UNAUTHORIZED);
        }
        return guestService.UserToken(jwtRequest);
    }

}
