package com.github.alym62.springterraform.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.alym62.springterraform.domain.User;
import com.github.alym62.springterraform.exceptions.BusinessException;
import com.github.alym62.springterraform.payload.UserRequestDTO;
import com.github.alym62.springterraform.payload.UserResponseDTO;
import com.github.alym62.springterraform.repository.UserRepository;
import com.github.alym62.springterraform.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Override
    public Optional<UserResponseDTO> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(user -> new UserResponseDTO(user.getId()));
    }

    @Override
    public UUID createUser(UserRequestDTO dto) {
        var userExists = findByEmail(dto.email());
        if (userExists.isPresent())
            throw new BusinessException("Ops! Dont possible save user.");

        var password = encoder.encode(dto.password());
        var userSaved = repository.save(User.builder()
                .email(dto.email())
                .name(dto.name())
                .password(password)
                .build());

        return userSaved.getId();
    }

}
