package com.nestoraluraoracleone.forohub.service;


import com.nestoraluraoracleone.forohub.exception.RecursoNoEncontradoException;
import com.nestoraluraoracleone.forohub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.stereotype.Service;

@Service
public class AutenticacionService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return usuarioRepository.findByCorreoElectronico(username)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario con correo " + username + " no encontrado."));
    }
}

