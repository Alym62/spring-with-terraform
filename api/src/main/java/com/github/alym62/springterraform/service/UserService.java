package com.github.alym62.springterraform.service;

import java.util.Optional;
import java.util.UUID;

import com.github.alym62.springterraform.payload.UserRequestDTO;
import com.github.alym62.springterraform.payload.UserResponseDTO;

public interface UserService {
    Optional<UserResponseDTO> findByEmail(String email);

    UUID createUser(UserRequestDTO dto);
}
