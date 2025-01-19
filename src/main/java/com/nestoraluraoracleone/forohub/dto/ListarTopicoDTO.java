package com.nestoraluraoracleone.forohub.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ListarTopicoDTO(
        @NotNull Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        String estado,
        String autor,
        String curso
) {}
