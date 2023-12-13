package com.example.microserviciopersona.controllers;

import com.example.microserviciopersona.dtos.DtoPersona;
import com.example.microserviciopersona.entitys.Persona;
import com.example.microserviciopersona.services.ServicePersona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/personas")
public class ControllerPersona {

    @Autowired
    private ServicePersona servicePersona;

    @GetMapping("")
    public ResponseEntity<?> getAll(){

        List<DtoPersona> dto= servicePersona.getAll();

        if(dto!=null) {
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al listar personas");
        }
    }

    @GetMapping("byId/{id}")
    public ResponseEntity<?>getById(@PathVariable int id){

        DtoPersona p= servicePersona.getById(id);

        if(p!=null) {
            return ResponseEntity.status(HttpStatus.OK).body(p);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. Persona no encontrada");
        }
    }

    @GetMapping("getIdPersona/{id}")
    public ResponseEntity<?>getIdPersona(@PathVariable int id){

        int idPersona = servicePersona.getIdPersona(id);

        if(idPersona>0) {
            return ResponseEntity.status(HttpStatus.OK).body(idPersona);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. Persona no encontrada");
        }
    }

    @GetMapping("datosPersonales/{id}")
    public ResponseEntity<?>getDatosById(@PathVariable int id){

        String datos= servicePersona.getDatosById(id);

        if(datos!=null) {
            return ResponseEntity.status(HttpStatus.OK).body(datos);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. Persona no encontrada");
        }
    }

    @PostMapping("")
    public ResponseEntity<?>create(@RequestBody DtoPersona p) throws Exception {

        try{
            servicePersona.create(p);
            return ResponseEntity.status(HttpStatus.CREATED).body(p);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear persona, ingrese campos correctos");
        }
    }

    @PutMapping("/{id}/{email}")
    public ResponseEntity<?>update(@PathVariable int id, @PathVariable String email){

        try {
            servicePersona.update(id, email);
            DtoPersona p = servicePersona.getById(id);

            return ResponseEntity.status(HttpStatus.OK).body(p);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al modificar. Verifique que la persona exista");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>delete(@PathVariable int id) throws Exception {

        DtoPersona aux = servicePersona.delete(id);
        if(aux != null)
            return ResponseEntity.status(HttpStatus.OK).body("Persona eliminada");
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. La persona que desea eliminar no existe");
        }
    }

    @GetMapping("cantPaginasRevisadas/{anio}")
    public ResponseEntity<?> getListadoPorCantidadDePaginasRevisadasPorAnio(@PathVariable int anio) throws Exception {
        List<String> infoRevisores = servicePersona.getListadoPorCantidadDePaginasRevisadasPorAnio(anio);

        if (infoRevisores != null && !infoRevisores.isEmpty()) {
            return ResponseEntity.ok(infoRevisores);
        }else if(infoRevisores.isEmpty()){
            return ResponseEntity.badRequest().body(Collections.singletonList("Lista vacía"));
        } else {
            return ResponseEntity.badRequest().body(Collections.singletonList("Error al listar información de revisores"));
        }
    }
}
