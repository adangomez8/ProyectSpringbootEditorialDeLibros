package com.example.microservicioeditorial.security.Repository;

import com.example.microservicioeditorial.security.User.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositoryUsuario extends JpaRepository<Usuario, String> {

    Optional<Usuario> findByUsername(String username);
}
