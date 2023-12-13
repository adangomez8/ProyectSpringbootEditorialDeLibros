package com.example.microservicioeditorial.repositorys;

import com.example.microservicioeditorial.dtos.DtoReporteCapitulo;
import com.example.microservicioeditorial.entitys.ReporteCapitulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepositoryReporteCapitulo extends JpaRepository<ReporteCapitulo, Integer> {

    /**
     *  - Cuantas páginas reviso cada persona en un año dado.
     * */

    @Query("SELECT rc FROM ReporteCapitulo rc " +
            "WHERE rc.rol = 'revisor' AND YEAR(rc.fechaEdicion) = :anio AND rc.idPersona = :idPersona")
    List<DtoReporteCapitulo> getReportesRevisadosByAnioPorPersona(@Param("anio") int anio, @Param("idPersona") int idPersona);



}
