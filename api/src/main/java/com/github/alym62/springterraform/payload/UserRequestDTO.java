package com.github.alym62.springterraform.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

public record UserRequestDTO(
        @Schema(description = "User - name") @NotEmpty String name,
        @Schema(description = "User - email") @NotEmpty String email,
        @Schema(description = "User - password") @NotEmpty String password) {
}
