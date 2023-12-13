package com.example.microservicioeditorial.services;

import com.example.microservicioeditorial.dtos.DtoReporteLibro;
import com.example.microservicioeditorial.entitys.ReporteLibro;
import com.example.microservicioeditorial.repositorys.RepositoryReporteLibro;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceReporteLibro {

    @Autowired
    private RepositoryReporteLibro repositoryReporteLibro;

    @Autowired
    private RestTemplate template;

    @Transactional
    public DtoReporteLibro getById(@PathVariable int id){

        Optional<ReporteLibro> aux = repositoryReporteLibro.findById(id);

        if(aux.isPresent()) {
            ReporteLibro rl=aux.get();
            return transformDTO(rl);
        }

        return null;
    }

    @Transactional
    public List<DtoReporteLibro> getAll(){

        List<ReporteLibro> reportes = repositoryReporteLibro.findAll();

        List<DtoReporteLibro> dtos = reportes.stream()
                .map(rl -> new DtoReporteLibro(rl.getIdLibro(), rl.getIdPersona(), rl.getRol(), rl.getFechaEdicion()))
                .collect(Collectors.toList());

        return dtos;
    }

    @Transactional
    public DtoReporteLibro create(@RequestBody DtoReporteLibro rl) throws Exception {

        try {

            ResponseEntity<Integer> responseidLibro = template.getForEntity("http://localhost:8080/libros/getIdLibro/" + rl.getIdLibro(), Integer.class);

            ResponseEntity<Integer> responseidPersona = template.getForEntity("http://localhost:8081/personas/getIdPersona/" + rl.getIdPersona(), Integer.class);

            if (responseidLibro.getBody() != null && responseidPersona.getBody() != null) {

                ReporteLibro nuevoReporte = new ReporteLibro(rl.getIdLibro(), rl.getIdPersona(), rl.getRol(), rl.getFechaEdicion());
                repositoryReporteLibro.save(nuevoReporte);
                return transformDTO(nuevoReporte);
            } else {
                return null;
            }
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public DtoReporteLibro update(int id, int idPersona) {

        Optional<ReporteLibro> aux = repositoryReporteLibro.findById(id);

        ResponseEntity<Integer> responseidPersona= template.getForEntity("http://localhost:8081/personas/getIdPersona/" + idPersona ,Integer.class);

        if(aux.isPresent() && responseidPersona.getBody() != null) {

            ReporteLibro rl=aux.get();
            rl.setIdPersona(idPersona);

            repositoryReporteLibro.save(rl);

            return transformDTO(rl);
        }else {
            return null;
        }
    }

    @Transactional
    public DtoReporteLibro delete (int id) throws Exception {

        Optional<ReporteLibro>aux = repositoryReporteLibro.findById(id);

        if(aux.isPresent()){

            try {
                repositoryReporteLibro.deleteById(id);
                return transformDTO(aux.get());
            }catch (Exception e){
                throw new Exception();
            }

        }else return null;

    }

    @Transactional
    public List<String> getInfoEditoresPorAnio(@PathVariable int anio) {
        List<String> editoresInfo = new ArrayList<>();
        List<Integer> idPersonas = repositoryReporteLibro.getListaEditoresByAnio(anio);

        for (Integer id : idPersonas) {
            try {
                ResponseEntity<Integer> responseIdPersona = template.getForEntity("http://localhost:8081/personas/getIdPersona/" + id, Integer.class);
                ResponseEntity<String> responseDatosPersona = template.getForEntity("http://localhost:8081/personas/datosPersonales/" + id, String.class);

                editoresInfo.add(responseDatosPersona.getBody() + " - Cantidad de libros editados: " +
                        repositoryReporteLibro.getCantidadLibrosEditados(responseIdPersona.getBody()));
            } catch (Exception e) {
                editoresInfo.add("Error al obtener información para el editor con ID " + id);
            }
        }

        return editoresInfo;
    }

    @Transactional
    public List<String> getInfoAutoresByParticipacion() {
        List<String> autoresInfo = new ArrayList<>();
        List<Integer> idPersonas = repositoryReporteLibro.getListaAutores();

        for (Integer id : idPersonas) {
            try {
                ResponseEntity<Integer> responseIdPersona = template.getForEntity("http://localhost:8081/personas/getIdPersona/" + id, Integer.class);
                ResponseEntity<String> responseDatosPersona = template.getForEntity("http://localhost:8081/personas/datosPersonales/" + id, String.class);

                autoresInfo.add(responseDatosPersona.getBody() + " - Cantidad de libros que participó como autor: " +
                        repositoryReporteLibro.getCantidadLibrosParticipoAutor(responseIdPersona.getBody()));
            } catch (Exception e) {
                autoresInfo.add("Error al obtener información para el autor con ID " + id);
            }
        }

        return autoresInfo;
    }



    private DtoReporteLibro transformDTO(ReporteLibro rl){

        return new DtoReporteLibro(rl.getIdLibro(), rl.getIdPersona(), rl.getRol(), rl.getFechaEdicion());
    }
}
