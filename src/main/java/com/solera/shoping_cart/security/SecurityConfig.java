package com.solera.shoping_cart.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {

   @Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable()) // Desactiva CSRF para facilitar pruebas
        .authorizeHttpRequests(auth -> auth
            .anyRequest().permitAll() // Permitir todo sin autenticación
        )
        .httpBasic(httpBasic -> httpBasic.disable()); // Desactiva completamente auth básica
    return http.build();
}
}
