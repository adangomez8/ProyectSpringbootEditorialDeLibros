package com.example.microservicioLibro.dtos;

public class DtoCapitulo {

    private String nombreCapitulo;

    private int cantPaginas;

    private int idLibro;


    public DtoCapitulo(String nombreCapitulo, int cantPaginas, int idLibro) {
        this.nombreCapitulo = nombreCapitulo;
        this.cantPaginas = cantPaginas;
        this.idLibro = idLibro;
    }

    public String getNombreCapitulo() {
        return nombreCapitulo;
    }

    public int getCantPaginas() {
        return cantPaginas;
    }

    public int getIdLibro() {
        return idLibro;
    }
}
