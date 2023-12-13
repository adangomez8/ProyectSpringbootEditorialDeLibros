package com.example.microserviciopersona.repositorys;

import com.example.microserviciopersona.entitys.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryPersona extends JpaRepository<Persona, Integer> {


}
