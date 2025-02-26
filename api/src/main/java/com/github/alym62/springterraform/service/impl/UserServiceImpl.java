package com.github.alym62.springterraform.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.alym62.springterraform.domain.User;
import com.github.alym62.springterraform.exceptions.BusinessException;
import com.github.alym62.springterraform.payload.UserIdResponseDTO;
import com.github.alym62.springterraform.payload.UserRequestDTO;
import com.github.alym62.springterraform.payload.UserResponseDTO;
import com.github.alym62.springterraform.payload.UserUpdateRequestDTO;
import com.github.alym62.springterraform.repository.UserRepository;
import com.github.alym62.springterraform.service.UserService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Override
    public Page<UserResponseDTO> getPager(Pageable pageable) {
        return repository.findAll(pageable)
                .map(user -> new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(),
                        user.getUpdatedAt()));
    }

    @Override
    public Optional<UserIdResponseDTO> getByEmail(String email) {
        return repository.findByEmail(email)
                .map(user -> new UserIdResponseDTO(user.getId()));
    }

    @Override
    @Transactional
    public UserIdResponseDTO createUser(UserRequestDTO dto) {
        var userExists = getByEmail(dto.email());
        if (userExists.isPresent())
            throw new BusinessException("Ops! Dont possible save user.");

        var password = encoder.encode(dto.password());
        var userSaved = repository.save(User.builder()
                .email(dto.email())
                .name(dto.name())
                .password(password)
                .build());

        return new UserIdResponseDTO(userSaved.getId());
    }

    @Override
    public UserResponseDTO getUserById(UUID id) {
        return repository.findById(id)
                .map(user -> new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(),
                        user.getUpdatedAt()))
                .orElseThrow(() -> new BusinessException("Ops! User not found"));
    }

    @Override
    public void deleteById(UUID id) {
        var user = getUserById(id);
        repository.deleteById(user.id());
    }

    @Override
    @Transactional
    public void updateById(UUID id, UserUpdateRequestDTO dto) {
        var user = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Ops! User not found"));

        user.setName(dto.name());
        user.setPassword(encoder.encode(dto.password()));

        repository.save(user);
    }

    @Override
    public UserResponseDTO userLogged() {
        var ctx = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var response = (UserResponseDTO) ctx;
        return new UserResponseDTO(response.id(), response.name(), response.email(), response.createdAt(),
                response.updatedAt());
    }
}
