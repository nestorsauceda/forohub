package com.nestoraluraoracleone.forohub.dto;


import com.nestoraluraoracleone.forohub.model.EstadoTopico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ActualizarTopicoDTO(
        @NotBlank String titulo,
        @NotBlank String mensaje,
        @NotNull EstadoTopico status,
        @NotNull Long autorId,
        @NotNull Long cursoId
) {}
