package com.example.microservicioeditorial.services;

import com.example.microservicioeditorial.security.Config.AuthResponse;
import com.example.microservicioeditorial.security.Config.JwtService;
import com.example.microservicioeditorial.security.Config.Login;
import com.example.microservicioeditorial.security.Config.Register;
import com.example.microservicioeditorial.security.Repository.RepositoryUsuario;
import com.example.microservicioeditorial.security.User.Rol;
import com.example.microservicioeditorial.security.User.Usuario;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceAuth {

    private final RepositoryUsuario repositoryUsuario;

    private final JwtService jwtService;
    public AuthResponse login(Login request) {

        Usuario user = Usuario.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build();


        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }

    public AuthResponse register(Register request) {
        Usuario user = Usuario.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .country(request.getCountry())
                .role(Rol.USER) //se crea coomo usuario
                .build();

        repositoryUsuario.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}
