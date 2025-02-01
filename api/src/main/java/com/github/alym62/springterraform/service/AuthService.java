package com.github.alym62.springterraform.service;

import com.github.alym62.springterraform.payload.AuthRequestDTO;
import com.github.alym62.springterraform.payload.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO authenticate(AuthRequestDTO dto);
}
