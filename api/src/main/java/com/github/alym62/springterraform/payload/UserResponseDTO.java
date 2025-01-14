package com.github.alym62.springterraform.payload;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserResponseDTO(
        @Schema(description = "User - ID") UUID id) {
}
