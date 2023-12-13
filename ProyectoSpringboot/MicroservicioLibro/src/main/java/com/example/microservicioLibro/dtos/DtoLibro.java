package com.example.microservicioLibro.dtos;

public class DtoLibro {

    private String titulo;

    private int anioPublicacion;

    public DtoLibro(String titulo, int anioPublicacion) {
        this.titulo = titulo;
        this.anioPublicacion = anioPublicacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }
}
