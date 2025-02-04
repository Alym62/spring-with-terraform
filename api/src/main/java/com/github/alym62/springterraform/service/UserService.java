package com.github.alym62.springterraform.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.github.alym62.springterraform.payload.UserIdResponseDTO;
import com.github.alym62.springterraform.payload.UserRequestDTO;
import com.github.alym62.springterraform.payload.UserResponseDTO;
import com.github.alym62.springterraform.payload.UserUpdateRequestDTO;

public interface UserService {
    Page<UserResponseDTO> getPager(Pageable pageable);

    Optional<UserIdResponseDTO> getByEmail(String email);

    UserIdResponseDTO createUser(UserRequestDTO dto);

    UserResponseDTO getUserById(UUID id);

    void deleteById(UUID id);

    void updateById(UUID id, UserUpdateRequestDTO dto);

    UserResponseDTO userLogged();
}
