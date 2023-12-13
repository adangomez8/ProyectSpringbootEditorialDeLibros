package com.example.microserviciopersona.dtos;

public class DtoPersona {

    private String nombre;

    private String apellido;

    private String email;

    public DtoPersona(String nombre, String apellido, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }
}
