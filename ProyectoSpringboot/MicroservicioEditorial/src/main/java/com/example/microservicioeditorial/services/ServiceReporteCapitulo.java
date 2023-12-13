package com.example.microservicioeditorial.services;

import com.example.microservicioeditorial.dtos.DtoReporteCapitulo;
import com.example.microservicioeditorial.entitys.ReporteCapitulo;
import com.example.microservicioeditorial.repositorys.RepositoryReporteCapitulo;
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
public class ServiceReporteCapitulo {

    @Autowired
    private RepositoryReporteCapitulo repositoryReporteCapitulo;

    @Autowired
    private RestTemplate template;

    @Transactional
    public DtoReporteCapitulo getById(@PathVariable int id){

        Optional<ReporteCapitulo> aux = repositoryReporteCapitulo.findById(id);

        if(aux.isPresent()) {
            ReporteCapitulo rc=aux.get();
            return transformDTO(rc);
        }

        return null;
    }

    @Transactional
    public List<DtoReporteCapitulo> getAll(){

        List<ReporteCapitulo> reportes = repositoryReporteCapitulo.findAll();

        List<DtoReporteCapitulo> dtos = reportes.stream()
                .map(rc -> new DtoReporteCapitulo(rc.getIdCapitulo(), rc.getIdPersona(), rc.getRol(), rc.getFechaEdicion()))
                .collect(Collectors.toList());

        return dtos;
    }

    @Transactional
    public DtoReporteCapitulo create(@RequestBody ReporteCapitulo rc) {

        ResponseEntity<Integer> responseidCapitulo= template.getForEntity("http://localhost:8080/capitulos " + rc.getIdCapitulo() ,Integer.class);

        ResponseEntity<Integer> responseidPersona= template.getForEntity("http://localhost:8081/personas " + rc.getIdPersona() ,Integer.class);

        if(!repositoryReporteCapitulo.existsById(rc.getId()) && responseidCapitulo.getBody()!=null && responseidPersona.getBody()!=null) {

            repositoryReporteCapitulo.save(rc);
            return transformDTO(rc);
        }else{
            return null;
        }
    }

    @Transactional
    public DtoReporteCapitulo update(int id, int idPersona) {

        Optional<ReporteCapitulo> aux = repositoryReporteCapitulo.findById(id);

        ResponseEntity<Integer> responseidPersona= template.getForEntity("http://localhost:8081/personas " + idPersona ,Integer.class);

        if(aux.isPresent() && responseidPersona.getBody() != null) {

            ReporteCapitulo rc=aux.get();
            rc.setIdPersona(idPersona);

            repositoryReporteCapitulo.save(rc);

            return transformDTO(rc);
        }else {
            return null;
        }
    }

    @Transactional
    public DtoReporteCapitulo delete (int id) throws Exception {

        Optional<ReporteCapitulo>aux = repositoryReporteCapitulo.findById(id);

        if(aux.isPresent()){

            try {
                repositoryReporteCapitulo.deleteById(id);
                return transformDTO(aux.get());
            }catch (Exception e){
                throw new Exception();
            }

        }else return null;

    }

    @Transactional
    public List<Integer> getReportesRevisadosByAnioPorPersona(@PathVariable int anio, @PathVariable int idPersona){

        List<Integer> capitulos = new ArrayList<>();
        List<DtoReporteCapitulo> reportesRevisados = repositoryReporteCapitulo.getReportesRevisadosByAnioPorPersona(anio, idPersona);

        for (DtoReporteCapitulo dto : reportesRevisados){
            capitulos.add(dto.getIdCapitulo());
        }
        return capitulos;
    }

    @Transactional
    public int getIdCapitulo(@PathVariable int id){

        Optional<ReporteCapitulo> aux = repositoryReporteCapitulo.findById(id);

        if(aux.isPresent()) {
            ReporteCapitulo rc=aux.get();
            return rc.getIdCapitulo();
        }

        return 0;
    }


    private DtoReporteCapitulo transformDTO(ReporteCapitulo rc){

        return new DtoReporteCapitulo(rc.getIdCapitulo(), rc.getIdPersona(), rc.getRol(), rc.getFechaEdicion());
    }
}
