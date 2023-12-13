package com.example.microservicioLibro.repositorys;

import com.example.microservicioLibro.entitys.Capitulo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryCapitulo extends JpaRepository<Capitulo, Integer> {
}
