package com.example.client_processing.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtils {

    @Value("${jwt.secret}")
    private String secretKey;

    public String createToken(UserDetails user) {
        Map<String, Object> claims = new HashMap<>();
        List<String> roleList = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("username", user.getUsername());
        claims.put("role", roleList);
        return Jwts.builder()
                .claims(claims)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(30, ChronoUnit.MINUTES)))
                .signWith(getSecretKey())
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).get("username", String.class);
    }

    public SecretKey getSecretKey() {
        var key = new SecretKeySpec(Base64.getDecoder().decode(secretKey), SignatureAlgorithm.HS256.getJcaName());
        return key;
    }

    public List<String> getRoleFromToken(String token) {
        List<String> roleList = getClaimsFromToken(token).get("role", List.class);
        return roleList;
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
