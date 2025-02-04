package com.github.alym62.springterraform.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.github.alym62.springterraform.payload.AuthRequestDTO;
import com.github.alym62.springterraform.payload.AuthResponseDTO;
import com.github.alym62.springterraform.service.AuthService;
import com.github.alym62.springterraform.service.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager manager;
    private final JwtService jwtService;

    @Override
    public AuthResponseDTO authenticate(AuthRequestDTO dto) {
        var authToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var auth = manager.authenticate(authToken);

        return new AuthResponseDTO(jwtService.generateToken((UserDetails) auth.getPrincipal()));
    }

}
