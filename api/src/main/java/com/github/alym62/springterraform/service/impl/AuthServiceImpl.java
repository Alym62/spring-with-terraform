package com.github.alym62.springterraform.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.github.alym62.springterraform.payload.AuthRequestDTO;
import com.github.alym62.springterraform.payload.AuthResponseDTO;
import com.github.alym62.springterraform.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager manager;

    @Override
    public AuthResponseDTO authenticate(AuthRequestDTO dto) {
        var authToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var auth = manager.authenticate(authToken);

        return new AuthResponseDTO("accessToken");
    }

}
