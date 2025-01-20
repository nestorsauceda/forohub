package com.nestoraluraoracleone.forohub.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generarToken(Authentication authentication) {
        User usuario = (User) authentication.getPrincipal();
        return JWT.create()
                .withSubject(usuario.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .withIssuer("API Forohub")
                .sign(Algorithm.HMAC256(secret));
    }

    public String obtenerUsuarioDelToken(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("API Forohub")
                .build()
                .verify(token)
                .getSubject();
    }

    public boolean esTokenValido(String token) {
        try {
            JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer("API Forohub")
                    .build()
                    .verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
