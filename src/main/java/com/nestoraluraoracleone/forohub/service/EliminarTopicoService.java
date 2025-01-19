package com.nestoraluraoracleone.forohub.service;

import com.nestoraluraoracleone.forohub.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EliminarTopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    public void eliminarPorId(Long id) {
        // Verificar si el tópico existe
        if (!topicoRepository.existsById(id)) {
            throw new IllegalArgumentException("El tópico con ID " + id + " no existe.");
        }
        // Eliminar el tópico
        topicoRepository.deleteById(id);
    }
}
