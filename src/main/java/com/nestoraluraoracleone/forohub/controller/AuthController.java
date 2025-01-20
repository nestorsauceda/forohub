package com.nestoraluraoracleone.forohub.controller;

import com.nestoraluraoracleone.forohub.dto.LoginDTO;
import com.nestoraluraoracleone.forohub.repository.UsuarioRepository;
import com.nestoraluraoracleone.forohub.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository; // Inyección del repositorio

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO) {
        try {
            // Verificar si el usuario existe
            if (!usuarioRepository.findByCorreoElectronico(loginDTO.email()).isPresent()) {
                return ResponseEntity.status(404).body("{ \"error\": \"Usuario no encontrado.\" }");
            }

            // Realizar la autenticación
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password())
            );

            // Generar el token JWT
            String token = tokenService.generarToken(auth);

            // Devolver el token en formato JSON con tipo
            return ResponseEntity.ok().body("{ \"token\": \"" + token + "\", \"type\": \"Bearer\" }");

        } catch (AuthenticationException e) {
            // Credenciales inválidas: devolver 401
            return ResponseEntity.status(401).body("{ \"error\": \"Credenciales inválidas.\" }");
        }
    }
}