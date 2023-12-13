package com.example.microservicioeditorial.security.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity // Habilita la seguridad en los metodos de la app
@RequiredArgsConstructor
//@EnableMethodSecurity // Seguridad a nivel de metodos
public class SecurityConfig {

    private final FiltroJWT jwtAuthenticationFilter;

    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(csrf -> csrf.disable()).
                authorizeHttpRequests(authRequest -> authRequest.requestMatchers("/reportesLibros/**").
                        permitAll().anyRequest().
                        authenticated()).
                sessionManagement(sessionManager -> sessionManager.
                        sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
                authenticationProvider(authProvider).
                addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


}
