package com.github.alym62.springterraform.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.github.alym62.springterraform.payload.UserResponseDTO;

public interface JwtService {
    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token);

    UserResponseDTO getUserDetails(String token);
}
