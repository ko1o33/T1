package com.example.client_processing.dto.Jwt;

import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;
}
