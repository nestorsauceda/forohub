package com.nestoraluraoracleone.forohub.service;

import com.nestoraluraoracleone.forohub.exception.RecursoNoEncontradoException;
import com.nestoraluraoracleone.forohub.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EliminarTopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    public void eliminarPorId(Long id) {
        if (!topicoRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("El tópico con ID " + id + " no existe.");
        }
        topicoRepository.deleteById(id);
    }
}

