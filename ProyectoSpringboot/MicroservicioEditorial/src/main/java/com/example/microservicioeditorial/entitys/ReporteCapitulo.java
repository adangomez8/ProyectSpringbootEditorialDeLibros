package com.example.microservicioeditorial.entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class ReporteCapitulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int idCapitulo;

    private int idPersona;

    /**
     * autor - revisor - editor
     * */
    private String rol;

    private Date fechaEdicion;

    public ReporteCapitulo(int idCapitulo, int idPersona, String rol, Date fechaEdicion) {
        this.idCapitulo = idCapitulo;
        this.idPersona = idPersona;
        this.rol = rol;
        this.fechaEdicion = fechaEdicion;
    }

    public ReporteCapitulo(){

    }
}
