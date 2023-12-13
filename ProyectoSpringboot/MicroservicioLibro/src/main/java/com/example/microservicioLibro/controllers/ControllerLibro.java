package com.example.microservicioLibro.controllers;

import com.example.microservicioLibro.dtos.DtoLibro;
import com.example.microservicioLibro.entitys.Libro;
import com.example.microservicioLibro.services.ServiceLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libros")
public class ControllerLibro {

    @Autowired
    private ServiceLibro serviceLibro;

    @GetMapping("")
    public ResponseEntity<?>getAll(){

        List<DtoLibro> dto=serviceLibro.getAll();

        if(dto!=null) {
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al listar libros");
        }
    }

    @GetMapping("byId/{id}")
    public ResponseEntity<?>getById(@PathVariable int id){

        DtoLibro l= serviceLibro.getById(id);

        if(l!=null) {
            return ResponseEntity.status(HttpStatus.OK).body(l);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. Libro no encontrado");
        }
    }

    @GetMapping("getIdLibro/{id}")
    public ResponseEntity<?>getIdLibro(@PathVariable int id){

        int idLibro = serviceLibro.getIdLibro(id);

        if(idLibro>0) {
            return ResponseEntity.status(HttpStatus.OK).body(idLibro);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. Libro no encontrado");
        }
    }

    @PostMapping("")
    public ResponseEntity<?>create(@RequestBody DtoLibro l) throws Exception {

        try{
            serviceLibro.create(l);
            return ResponseEntity.status(HttpStatus.CREATED).body(l);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear libro, ingrese campos correctos");
        }
    }

    @PutMapping("/{id}/{titulo}")
    public ResponseEntity<?>update(@PathVariable int id, @PathVariable String titulo){

        try {
            serviceLibro.update(id, titulo);
            DtoLibro l = serviceLibro.getById(id);

            return ResponseEntity.status(HttpStatus.OK).body(l);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al modificar.Verifique que el libro exista");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>delete(@PathVariable int id) throws Exception {

        DtoLibro aux = serviceLibro.delete(id);
        if(aux != null)
            return ResponseEntity.status(HttpStatus.OK).body("Libro eliminado");
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. El libro que desea eliminar no existe");
        }
    }
}
