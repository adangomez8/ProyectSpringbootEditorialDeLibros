package com.example.microservicioeditorial.controllers;

import com.example.microservicioeditorial.dtos.DtoReporteLibro;
import com.example.microservicioeditorial.entitys.ReporteLibro;
import com.example.microservicioeditorial.security.Config.AuthResponse;
import com.example.microservicioeditorial.security.Config.Login;
import com.example.microservicioeditorial.security.Config.Register;
import com.example.microservicioeditorial.services.ServiceAuth;
import com.example.microservicioeditorial.services.ServiceReporteLibro;
import jakarta.security.auth.message.config.AuthConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/reportesLibros")
public class ControllerReporteLibro {

    @Autowired
    private ServiceReporteLibro serviceReporteLibro;

    @Autowired
    private  ServiceAuth serviceAuth;

    @GetMapping("")
    public ResponseEntity<?> getAll(){

        List<DtoReporteLibro> dto= serviceReporteLibro.getAll();

        if(dto!=null) {
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al listar reporte de libros");
        }
    }

    @GetMapping("byId/{id}")
    public ResponseEntity<?>getById(@PathVariable int id){

        DtoReporteLibro rl= serviceReporteLibro.getById(id);

        if(rl!=null) {
            return ResponseEntity.status(HttpStatus.OK).body(rl);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. Reporte no encontrado");
        }
    }

    @PostMapping("")
    public ResponseEntity<?>create(@RequestBody DtoReporteLibro rl) throws Exception {

        try{
            serviceReporteLibro.create(rl);
            return ResponseEntity.status(HttpStatus.CREATED).body(rl);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear reporte, ingrese campos correctos");
        }
    }

    @PutMapping("/{id}/{idPersona}")
    public ResponseEntity<?>update(@PathVariable int id, @PathVariable int idPersona){

        try {
            serviceReporteLibro.update(id, idPersona);
            DtoReporteLibro rl = serviceReporteLibro.getById(id);

            return ResponseEntity.status(HttpStatus.OK).body(rl);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al modificar.Verifique que el reporte exista");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>delete(@PathVariable int id) throws Exception {

        DtoReporteLibro aux = serviceReporteLibro.delete(id);
        if(aux != null)
            return ResponseEntity.status(HttpStatus.OK).body("reporte eliminado");
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. El reporte que desea eliminar no existe");
        }
    }

    @GetMapping("byAnio/{anio}")
    public ResponseEntity<?> getInfoEditoresPorAnio(@PathVariable int anio) {
        List<String> infoEditores = serviceReporteLibro.getInfoEditoresPorAnio(anio);

        if (infoEditores != null && !infoEditores.isEmpty()) {
            return ResponseEntity.ok(infoEditores);
        } else {
            return ResponseEntity.badRequest().body(Collections.singletonList("Error al listar información de editores"));
        }
    }

    @GetMapping("byParticipacionByAutor")
    public ResponseEntity<?> getInfoAutoresByParticipacion() {
        List<String> infoAutores = serviceReporteLibro.getInfoAutoresByParticipacion();

        if (infoAutores != null && !infoAutores.isEmpty()) {
            return ResponseEntity.ok(infoAutores);
        }else if(infoAutores.isEmpty()){
                return ResponseEntity.badRequest().body(Collections.singletonList("Lista vacía"));
        } else {
            return ResponseEntity.badRequest().body(Collections.singletonList("Error al listar información de autores"));
        }
    }

    //    @PostMapping("/login")
//    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
//        return ResponseEntity.ok(authService.login(loginRequest));
//    }
//    @PostMapping("/register")
//    public ResponseEntity<AuthResponse> register(@RequestBody LoginRequest loginRequest){
//        return ResponseEntity.ok(authService.register(loginRequest));
//    }

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody Login request){
        return ResponseEntity.ok(serviceAuth.login(request));
    }
    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody Register request){
        return ResponseEntity.ok(serviceAuth.register(request));
    }

}
