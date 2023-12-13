package com.example.microservicioLibro.controllers;


import com.example.microservicioLibro.dtos.DtoCapitulo;
import com.example.microservicioLibro.entitys.Capitulo;
import com.example.microservicioLibro.services.ServiceCapitulo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/capitulos")
public class ControllerCapitulo {

    @Autowired
    private ServiceCapitulo serviceCapitulo;

    @GetMapping("")
    public ResponseEntity<?> getAll(){

        List<DtoCapitulo> dto= serviceCapitulo.getAll();

        if(dto!=null) {
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al listar capítulos");
        }
    }

    @GetMapping("byId/{id}")
    public ResponseEntity<?>getById(@PathVariable int id){

        DtoCapitulo c= serviceCapitulo.getById(id);

        if(c!=null) {
            return ResponseEntity.status(HttpStatus.OK).body(c);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. Capítulo no encontrado");
        }
    }

    @GetMapping("getCantPaginas/{id}")
    public ResponseEntity<?>getIdLibro(@PathVariable int id){

        int cantPaginas = serviceCapitulo.getCantPaginas(id);

        if(cantPaginas>-1) {
            return ResponseEntity.status(HttpStatus.OK).body(cantPaginas);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. Capitulo no encontrado");
        }
    }

    @PostMapping("")
    public ResponseEntity<?>create(@RequestBody DtoCapitulo c) throws Exception {

        try{
            serviceCapitulo.create(c);
            return ResponseEntity.status(HttpStatus.CREATED).body(c);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear capítulo, ingrese campos correctos");
        }
    }

    @PutMapping("/{id}/{cantPag}")
    public ResponseEntity<?>update(@PathVariable int id, @PathVariable int cantPag){

        try {
            serviceCapitulo.update(id, cantPag);
            DtoCapitulo c = serviceCapitulo.getById(id);

            return ResponseEntity.status(HttpStatus.OK).body(c);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al modificar.Verifique que el capítulo exista");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>delete(@PathVariable int id) throws Exception {

        DtoCapitulo aux = serviceCapitulo.delete(id);
        if(aux != null)
            return ResponseEntity.status(HttpStatus.OK).body("Capítulo eliminado");
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. El capítulo que desea eliminar no existe");
        }
    }
}
