package com.nestoraluraoracleone.forohub.service;

import com.nestoraluraoracleone.forohub.dto.ActualizarTopicoDTO;
import com.nestoraluraoracleone.forohub.model.Curso;
import com.nestoraluraoracleone.forohub.model.Topico;
import com.nestoraluraoracleone.forohub.model.Usuario;
import com.nestoraluraoracleone.forohub.repository.CursoRepository;
import com.nestoraluraoracleone.forohub.repository.TopicoRepository;
import com.nestoraluraoracleone.forohub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ActualizarTopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public void actualizarTopico(Long id, ActualizarTopicoDTO topicoDTO) {
        // Verificar existencia del tópico
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El tópico con ID " + id + " no existe."));

        // Verificar duplicados (exceptuando el mismo tópico que se está actualizando)
        Optional<Topico> duplicado = topicoRepository.findByTituloAndMensaje(topicoDTO.titulo(), topicoDTO.mensaje());
        if (duplicado.isPresent() && !duplicado.get().getId().equals(id)) {
            throw new IllegalArgumentException("Ya existe un tópico con el mismo título y mensaje.");
        }

        // Obtener autor y curso
        Usuario autor = usuarioRepository.findById(topicoDTO.autorId())
                .orElseThrow(() -> new IllegalArgumentException("El autor no existe."));
        Curso curso = cursoRepository.findById(topicoDTO.cursoId())
                .orElseThrow(() -> new IllegalArgumentException("El curso no existe."));

        // Actualizar los datos del tópico
        topico.setTitulo(topicoDTO.titulo());
        topico.setMensaje(topicoDTO.mensaje());
        topico.setEstado(topicoDTO.status());
        topico.setAutor(autor);
        topico.setCurso(curso);

        topicoRepository.save(topico);
    }
}
