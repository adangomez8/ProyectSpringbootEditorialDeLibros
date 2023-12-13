package com.example.microservicioeditorial.repositorys;

import com.example.microservicioeditorial.entitys.ReporteLibro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepositoryReporteLibro extends JpaRepository<ReporteLibro, Integer> {

/**
 * - Cuantos libros editó cada persona en un año dado.
 * - En cuantos libros participó cada persona como autor de capítulo.
 * */


@Query("SELECT rl.idPersona FROM ReporteLibro rl " +
        "WHERE rl.rol = 'editor' AND YEAR(rl.fechaEdicion) = :anio " +
        "GROUP BY rl.idPersona")
List<Integer> getListaEditoresByAnio(@Param("anio") int anio);

    @Query("SELECT COUNT(rl.idLibro) FROM ReporteLibro rl " +
            "WHERE rl.idPersona = :idPersona " +
            "GROUP BY rl.idPersona")
    List<Integer> getCantidadLibrosEditados(@Param("idPersona") int idPersona);
    @Query("SELECT rl.idPersona FROM ReporteLibro rl " +
            "WHERE rl.rol = 'autor' " +
            "GROUP BY rl.idPersona")
    List<Integer> getListaAutores();

    @Query("SELECT COUNT(rl.idLibro) FROM ReporteLibro rl " +
            "WHERE rl.idPersona = :idPersona AND rl.rol = 'autor' " +
            "GROUP BY rl.idPersona")
    List<Integer> getCantidadLibrosParticipoAutor(@Param("idPersona") int idPersona);
}
