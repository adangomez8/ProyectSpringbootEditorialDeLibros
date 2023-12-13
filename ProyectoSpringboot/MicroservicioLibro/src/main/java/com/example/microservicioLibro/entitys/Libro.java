package com.example.microservicioLibro.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String titulo;

    private int anioPublicacion;

    @JsonIgnore
    @OneToMany(mappedBy = "libro")
    private List<Capitulo> capitulos;


    public Libro(String titulo, int anioPublicacion) {
        this.titulo = titulo;
        this.anioPublicacion = anioPublicacion;
        this.capitulos = new ArrayList<>();
    }

    public Libro(){
        this.capitulos = new ArrayList<>();
    }

    public void agregarCapitulo(Capitulo c){

        if(!this.capitulos.contains(c)){
            this.capitulos.add(c);
        }
    }
}
