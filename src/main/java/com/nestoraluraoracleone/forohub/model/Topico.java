package com.nestoraluraoracleone.forohub.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Topico")
@Getter
@Setter
@NoArgsConstructor
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String mensaje;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Enumerated(EnumType.STRING) // Guardará el valor del Enum como texto
    @Column(nullable = false)
    private EstadoTopico estado;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;
}
