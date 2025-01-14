package com.github.alym62.springterraform.service;

import java.util.UUID;

import com.github.alym62.springterraform.payload.UserRequestDTO;

public interface UserService {
    UUID createUser(UserRequestDTO dto);
}
