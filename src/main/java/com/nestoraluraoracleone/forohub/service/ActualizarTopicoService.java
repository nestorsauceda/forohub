package com.nestoraluraoracleone.forohub.service;

import com.nestoraluraoracleone.forohub.dto.ActualizarTopicoDTO;
import com.nestoraluraoracleone.forohub.exception.DatosInvalidosException;
import com.nestoraluraoracleone.forohub.exception.RecursoNoEncontradoException;
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
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El tópico con ID " + id + " no existe."));

        Optional<Topico> duplicado = topicoRepository.findByTituloAndMensaje(topicoDTO.titulo(), topicoDTO.mensaje());
        if (duplicado.isPresent() && !duplicado.get().getId().equals(id)) {
            throw new DatosInvalidosException("Ya existe un tópico con el mismo título y mensaje.");
        }

        Usuario autor = usuarioRepository.findById(topicoDTO.autorId())
                .orElseThrow(() -> new RecursoNoEncontradoException("El autor con ID " + topicoDTO.autorId() + " no existe."));
        Curso curso = cursoRepository.findById(topicoDTO.cursoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("El curso con ID " + topicoDTO.cursoId() + " no existe."));

        topico.setTitulo(topicoDTO.titulo());
        topico.setMensaje(topicoDTO.mensaje());
        topico.setEstado(topicoDTO.status());
        topico.setAutor(autor);
        topico.setCurso(curso);

        topicoRepository.save(topico);
    }
}

