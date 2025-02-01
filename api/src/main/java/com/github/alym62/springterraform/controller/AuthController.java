package com.github.alym62.springterraform.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.alym62.springterraform.payload.AuthRequestDTO;
import com.github.alym62.springterraform.payload.AuthResponseDTO;
import com.github.alym62.springterraform.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthRequestDTO dto) {
        return ResponseEntity.accepted().body(service.authenticate(dto));
    }

}
