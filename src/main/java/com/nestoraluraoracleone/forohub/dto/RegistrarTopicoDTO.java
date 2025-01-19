package com.nestoraluraoracleone.forohub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistrarTopicoDTO(
        @NotBlank String titulo,
        @NotBlank String mensaje,
        @NotNull Long autorId,
        @NotNull Long cursoId
) {
}

