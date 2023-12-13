package com.example.microservicioLibro.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Data
public class Capitulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombreCapitulo;

    private int cantPaginas;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="libro_id")
    private Libro libro;

    public Capitulo(){

    }

    public Capitulo(String nombreCapitulo, int cantPaginas, Libro libro) {
        this.nombreCapitulo = nombreCapitulo;
        this.cantPaginas = cantPaginas;
        this.libro = libro;
    }
}
