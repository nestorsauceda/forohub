package com.nestoraluraoracleone.forohub.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        String jsonResponse = String.format(
                "{\"status\": 403, \"error\": \"Forbidden\", \"message\": \"Acceso denegado. Debe autenticarse para acceder a este recurso.\", \"path\": \"%s\", \"timestamp\": \"%s\"}",
                request.getRequestURI(),
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
        );

        response.getWriter().write(jsonResponse);
    }
}