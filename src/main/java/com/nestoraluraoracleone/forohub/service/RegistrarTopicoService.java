package com.nestoraluraoracleone.forohub.service;

import com.nestoraluraoracleone.forohub.exception.DatosInvalidosException;
import com.nestoraluraoracleone.forohub.exception.RecursoNoEncontradoException;
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

    public Topico registrarTopico(RegistrarTopicoDTO topicoDTO) {
        Optional<Topico> duplicado = topicoRepository.findByTituloAndMensaje(topicoDTO.titulo(), topicoDTO.mensaje());
        if (duplicado.isPresent()) {
            throw new DatosInvalidosException("El tópico ya existe con el mismo título y mensaje.");
        }

        Usuario autor = usuarioRepository.findById(topicoDTO.autorId())
                .orElseThrow(() -> new RecursoNoEncontradoException("El autor con ID " + topicoDTO.autorId() + " no existe."));
        Curso curso = cursoRepository.findById(topicoDTO.cursoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("El curso con ID " + topicoDTO.cursoId() + " no existe."));

        Topico topico = new Topico();
        topico.setTitulo(topicoDTO.titulo());
        topico.setMensaje(topicoDTO.mensaje());
        topico.setAutor(autor);
        topico.setCurso(curso);
        topico.setEstado(EstadoTopico.ABIERTO);
        topico.setFechaCreacion(LocalDateTime.now());

        return topicoRepository.save(topico);
    }
}