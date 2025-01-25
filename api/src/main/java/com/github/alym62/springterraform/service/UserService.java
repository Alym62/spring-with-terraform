package com.github.alym62.springterraform.service;

import java.util.Optional;
import java.util.UUID;

import com.github.alym62.springterraform.payload.UserIdResponseDTO;
import com.github.alym62.springterraform.payload.UserRequestDTO;
import com.github.alym62.springterraform.payload.UserResponseDTO;

public interface UserService {
    Optional<UserIdResponseDTO> getByEmail(String email);

    UserIdResponseDTO createUser(UserRequestDTO dto);

    UserResponseDTO getUserById(UUID id);

    void deleteById(UUID id);
}
