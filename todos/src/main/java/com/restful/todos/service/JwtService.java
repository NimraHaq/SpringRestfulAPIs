package com.restful.todos.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {

    String extractUsername(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
    String generateToken(Map<String, Claims> claims, UserDetails userDetails);
}
