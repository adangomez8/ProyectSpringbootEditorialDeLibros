package com.example.microservicioeditorial.dtos;

import com.example.microservicioeditorial.entitys.ReporteCapitulo;

import java.util.Date;

public class DtoReporteCapitulo {

    private int idCapitulo;

    private int idPersona;

    private String rol;

    private Date fechaEdicion;

    public DtoReporteCapitulo(int idCapitulo, int idPersona, String rol, Date fechaEdicion) {
        this.idCapitulo = idCapitulo;
        this.idPersona = idPersona;
        this.rol = rol;
        this.fechaEdicion = fechaEdicion;
    }

    public DtoReporteCapitulo(ReporteCapitulo rc) {
        this.idCapitulo = rc.getIdCapitulo();
        this.idPersona = rc.getIdPersona();
        this.rol = rc.getRol();
        this.fechaEdicion = rc.getFechaEdicion();
    }
    public int getIdCapitulo() {
        return idCapitulo;
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
