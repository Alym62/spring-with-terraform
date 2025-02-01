package com.github.alym62.springterraform.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.alym62.springterraform.payload.UserIdResponseDTO;
import com.github.alym62.springterraform.payload.UserRequestDTO;
import com.github.alym62.springterraform.payload.UserResponseDTO;
import com.github.alym62.springterraform.payload.UserUpdateRequestDTO;
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

    @GetMapping("/pager")
    public ResponseEntity<Page<UserResponseDTO>> pager(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int perPage) {
        Pageable pageable = PageRequest.of(page, perPage);
        return ResponseEntity.ok().body(service.getPager(pageable));
    }

    @PostMapping("/create")
    public ResponseEntity<UserIdResponseDTO> create(@RequestBody @Valid UserRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(service.getUserById(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody UserUpdateRequestDTO dto) {
        service.updateById(id, dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<Object> getUserLogged() {
        return ResponseEntity.ok().body("Logged success");
    }

}
