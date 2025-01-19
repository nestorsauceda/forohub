package com.nestoraluraoracleone.forohub.service;
import com.nestoraluraoracleone.forohub.model.EstadoTopico;
import com.nestoraluraoracleone.forohub.dto.RegistrarTopicoDTO;
import com.nestoraluraoracleone.forohub.model.Curso;
import com.nestoraluraoracleone.forohub.model.Topico;
import com.nestoraluraoracleone.forohub.model.Usuario;
import com.nestoraluraoracleone.forohub.repository.CursoRepository;
import com.nestoraluraoracleone.forohub.repository.TopicoRepository;
import com.nestoraluraoracleone.forohub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RegistrarTopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public void registrarTopico(RegistrarTopicoDTO topicoDTO) {
        // Verificar duplicados
        Optional<Topico> duplicado = topicoRepository.findByTituloAndMensaje(topicoDTO.titulo(), topicoDTO.mensaje());
        if (duplicado.isPresent()) {
            throw new IllegalArgumentException("El tópico ya existe con el mismo título y mensaje.");
        }

        // Obtener autor y curso
        Usuario autor = usuarioRepository.findById(topicoDTO.autorId())
                .orElseThrow(() -> new IllegalArgumentException("El autor no existe."));
        Curso curso = cursoRepository.findById(topicoDTO.cursoId())
                .orElseThrow(() -> new IllegalArgumentException("El curso no existe."));

        // Crear y guardar el tópico
        Topico topico = new Topico();
        topico.setTitulo(topicoDTO.titulo());
        topico.setMensaje(topicoDTO.mensaje());
        topico.setAutor(autor);
        topico.setCurso(curso);
        topico.setEstado(EstadoTopico.ABIERTO); // Establecer un valor predeterminado para el estado
        topico.setFechaCreacion(LocalDateTime.now()); // Establecer la fecha de creación


        topicoRepository.save(topico);
    }
}
