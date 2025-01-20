package com.nestoraluraoracleone.forohub.service;

import com.nestoraluraoracleone.forohub.dto.ListarTopicoDTO;
import com.nestoraluraoracleone.forohub.exception.RecursoNoEncontradoException;
import com.nestoraluraoracleone.forohub.model.Topico;
import com.nestoraluraoracleone.forohub.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListarTopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    public List<ListarTopicoDTO> listarTopicos() {
        List<Topico> topicos = topicoRepository.findAll();
        return topicos.stream().map(topico -> new ListarTopicoDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getEstado().name(),
                topico.getAutor().getNombre(),
                topico.getCurso().getNombre()
        )).collect(Collectors.toList());
    }

    public ListarTopicoDTO obtenerPorId(Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El tópico con ID " + id + " no existe."));

        return new ListarTopicoDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getEstado().name(),
                topico.getAutor().getNombre(),
                topico.getCurso().getNombre()
        );
    }
}

