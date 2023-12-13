package com.example.microservicioLibro.services;

import com.example.microservicioLibro.dtos.DtoCapitulo;
import com.example.microservicioLibro.dtos.DtoLibro;
import com.example.microservicioLibro.entitys.Capitulo;
import com.example.microservicioLibro.entitys.Libro;
import com.example.microservicioLibro.repositorys.RepositoryCapitulo;
import com.example.microservicioLibro.repositorys.RepositoryLibro;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceCapitulo {

    @Autowired
    private RepositoryCapitulo repositoryCapitulo;

    @Autowired
    private RepositoryLibro repositoryLibro;

    @Transactional
    public DtoCapitulo getById(@PathVariable int id){

        Optional<Capitulo> aux = repositoryCapitulo.findById(id);

        if(aux.isPresent()) {
            Capitulo l=aux.get();
            return transformDTO(l);
        }

        return null;
    }

    @Transactional
    public List<DtoCapitulo> getAll(){

        List<Capitulo> capitulos = repositoryCapitulo.findAll();

        List<DtoCapitulo> dtos = capitulos.stream()
                .map(capitulo -> new DtoCapitulo(capitulo.getNombreCapitulo(), capitulo.getCantPaginas(), capitulo.getLibro().getId()))
                .collect(Collectors.toList());

        return dtos;
    }

    @Transactional
    public DtoCapitulo create(@RequestBody DtoCapitulo c) {

        if(repositoryLibro.existsById(c.getIdLibro())) {
            Libro libro = repositoryLibro.findById(c.getIdLibro()).get();
            Capitulo nuevoCapitulo = new Capitulo(c.getNombreCapitulo(), c.getCantPaginas(), libro);
            repositoryCapitulo.save(nuevoCapitulo);
            return transformDTO(nuevoCapitulo);
        }else{
            return null;
        }
    }

    @Transactional
    public DtoCapitulo update(int id, int cantPag) {

        Optional<Capitulo> aux = repositoryCapitulo.findById(id);

        if(aux.isPresent()) {

            Capitulo c=aux.get();
            c.setCantPaginas(cantPag);

            repositoryCapitulo.save(c);

            return transformDTO(c);
        }else {
            return null;
        }
    }

    @Transactional
    public DtoCapitulo delete (int id) throws Exception {

        Optional<Capitulo>aux = repositoryCapitulo.findById(id);

        if(aux.isPresent()){

            try {
                repositoryCapitulo.deleteById(id);
                return transformDTO(aux.get());
            }catch (Exception e){
                throw new Exception();
            }

        }else return null;

    }

    @Transactional
    public int getCantPaginas(@PathVariable int id){

        Optional<Capitulo> capitulo = repositoryCapitulo.findById(id);

        if(capitulo.isPresent()){
            return capitulo.get().getCantPaginas();
        }
        return -1;
    }

    private DtoCapitulo transformDTO(Capitulo c){

        return new DtoCapitulo(c.getNombreCapitulo(), c.getCantPaginas(), c.getLibro().getId());
    }
}
