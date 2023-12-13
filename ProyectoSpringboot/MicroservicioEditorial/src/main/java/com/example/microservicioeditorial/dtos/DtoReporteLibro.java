package com.example.microservicioeditorial.dtos;

import java.util.Date;

public class DtoReporteLibro {

    private int idLibro;

    private int idPersona;

    private String rol;

    private Date fechaEdicion;

    public DtoReporteLibro(int idLibro, int idPersona, String rol, Date fechaEdicion) {
        this.idLibro = idLibro;
        this.idPersona = idPersona;
        this.rol = rol;
        this.fechaEdicion = fechaEdicion;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public String getRol() {
        return rol;
    }

    public Date getFechaEdicion() {
        return fechaEdicion;
    }
}
