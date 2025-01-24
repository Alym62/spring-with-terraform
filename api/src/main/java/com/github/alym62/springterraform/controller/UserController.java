package com.github.alym62.springterraform.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.alym62.springterraform.payload.UserRequestDTO;
import com.github.alym62.springterraform.payload.UserResponseDTO;
import com.github.alym62.springterraform.service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/health")
    public ResponseEntity<Object> health() {
        return ResponseEntity.ok().body("Success");
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserRequestDTO dto) {
        var uuid = service.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponseDTO(uuid));
    }

}
