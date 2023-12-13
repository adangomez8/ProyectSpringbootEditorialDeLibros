package com.example.microservicioeditorial.entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class ReporteLibro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int idLibro;

    private int idPersona;

    /**
     * autor - revisor - editor
     * */
    private String rol;

    private Date fechaEdicion;

    public ReporteLibro(int idLibro, int idPersona, String rol, Date fechaEdicion) {
        this.idLibro = idLibro;
        this.idPersona = idPersona;
        this.rol = rol;
        this.fechaEdicion = fechaEdicion;
    }

    public ReporteLibro(){

    }
}
