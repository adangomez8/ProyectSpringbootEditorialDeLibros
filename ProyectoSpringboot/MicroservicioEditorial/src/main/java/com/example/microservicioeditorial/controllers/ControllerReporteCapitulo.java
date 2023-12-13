package com.example.microservicioeditorial.controllers;

import com.example.microservicioeditorial.dtos.DtoReporteCapitulo;
import com.example.microservicioeditorial.entitys.ReporteCapitulo;
import com.example.microservicioeditorial.services.ServiceReporteCapitulo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reportesCapitulos")
public class ControllerReporteCapitulo {

    @Autowired
    private ServiceReporteCapitulo serviceReporteCapitulo;

    @GetMapping("")
    public ResponseEntity<?> getAll(){

        List<DtoReporteCapitulo> dto= serviceReporteCapitulo.getAll();

        if(dto!=null) {
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al listar reporte de capítulos");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?>getById(@PathVariable int id){

        DtoReporteCapitulo rl= serviceReporteCapitulo.getById(id);

        if(rl!=null) {
            return ResponseEntity.status(HttpStatus.OK).body(rl);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. Reporte no encontrado");
        }
    }

    @PostMapping("")
    public ResponseEntity<?>create(@RequestBody ReporteCapitulo rc) throws Exception {

        try{
            serviceReporteCapitulo.create(rc);
            return ResponseEntity.status(HttpStatus.CREATED).body(rc);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear reporte, ingrese campos correctos");
        }
    }

    @PutMapping("/{id}/{titulo}")
    public ResponseEntity<?>update(@PathVariable int id, @PathVariable int idPersona){

        try {
            serviceReporteCapitulo.update(id, idPersona);
            DtoReporteCapitulo rl = serviceReporteCapitulo.getById(id);

            return ResponseEntity.status(HttpStatus.OK).body(rl);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al modificar.Verifique que el reporte exista");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>delete(@PathVariable int id) throws Exception {

        DtoReporteCapitulo aux = serviceReporteCapitulo.delete(id);
        if(aux != null)
            return ResponseEntity.status(HttpStatus.OK).body("reporte eliminado");
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. El reporte que desea eliminar no existe");
        }
    }


    @GetMapping("capitulosRevisadosByAnioPorPersona/{anio}/{idPersona}")
    public ResponseEntity<?> obtenerIdsCapitulosRevisadosPorAnioYPersona(@PathVariable int anio, @PathVariable int idPersona) {
        List<Integer> dto = serviceReporteCapitulo.getReportesRevisadosByAnioPorPersona(anio, idPersona);

        if (dto != null && !dto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron capítulos para los criterios especificados");
        }
    }

    @GetMapping("getIdCapituloDeReporte/{id}")
    public ResponseEntity<?>getIdCapituloDeReporte(@PathVariable int id){

        int idCapitulo = serviceReporteCapitulo.getIdCapitulo(id);

        if(idCapitulo>0) {
            return ResponseEntity.status(HttpStatus.OK).body(idCapitulo);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. Reporte no encontrado");
        }
    }



}
