package com.example.microservicioLibro.repositorys;

import com.example.microservicioLibro.entitys.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RepositoryLibro extends JpaRepository<Libro, Integer> {


}
