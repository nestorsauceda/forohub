package com.nestoraluraoracleone.forohub.config;

import com.nestoraluraoracleone.forohub.service.TokenService;
import com.nestoraluraoracleone.forohub.service.AutenticacionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final TokenService tokenService;
    private final AutenticacionService autenticacionService;

    public SecurityConfig(TokenService tokenService, AutenticacionService autenticacionService) {
        this.tokenService = tokenService;
        this.autenticacionService = autenticacionService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF (solo para desarrollo)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // Permitir acceso al endpoint de autenticación
                        .anyRequest().authenticated() // Requiere autenticación para otros endpoints
                )
                .addFilterBefore(new JwtAuthenticationFilter(tokenService, autenticacionService),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
