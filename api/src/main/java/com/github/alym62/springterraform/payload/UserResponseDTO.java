package com.github.alym62.springterraform.payload;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;

@Builder
public record UserResponseDTO(
                UUID id,
                String name,
                String email,
                LocalDateTime createdAt,
                LocalDateTime updatedAt) {
}
