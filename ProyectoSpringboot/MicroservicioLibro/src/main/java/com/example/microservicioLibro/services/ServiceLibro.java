package com.example.microservicioLibro.services;

import com.example.microservicioLibro.dtos.DtoLibro;
import com.example.microservicioLibro.entitys.Libro;
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
public class ServiceLibro {

    @Autowired
    private RepositoryLibro repositoryLibro;

    @Transactional
    public DtoLibro getById(@PathVariable int id){

        Optional<Libro> aux = repositoryLibro.findById(id);

        if(aux.isPresent()) {
            Libro l=aux.get();
            return transformDTO(l);
        }

        return null;
    }

    @Transactional
    public List<DtoLibro> getAll(){

        List<Libro> libros = repositoryLibro.findAll();

        List<DtoLibro> dtos = libros.stream()
                .map(libro -> new DtoLibro(libro.getTitulo(), libro.getAnioPublicacion()))
                .collect(Collectors.toList());

        return dtos;
    }

    @Transactional
    public DtoLibro create(@RequestBody DtoLibro l) {

        Libro nuevo = new Libro(l.getTitulo(), l.getAnioPublicacion());

        repositoryLibro.save(nuevo);

        return transformDTO(nuevo);
    }

    @Transactional
    public DtoLibro update(int id, String titulo) {

        Optional<Libro> aux = repositoryLibro.findById(id);

        if(aux.isPresent()) {

            Libro l=aux.get();
            l.setTitulo(titulo);

            repositoryLibro.save(l);

            return transformDTO(l);
        }else {
            return null;
        }
    }

    @Transactional
    public DtoLibro delete (int id) throws Exception {

        Optional<Libro>aux = repositoryLibro.findById(id);

        if(aux.isPresent()){

            try {
                repositoryLibro.deleteById(id);
                return transformDTO(aux.get());
            }catch (Exception e){
                throw new Exception();
            }

        }else return null;

    }

    @Transactional
    public int getIdLibro(@PathVariable int id){

        Optional<Libro> libro = repositoryLibro.findById(id);

        if(libro.isPresent()){
            return libro.get().getId();
        }
        return 0;
    }

    private DtoLibro transformDTO(Libro l){

        return new DtoLibro(l.getTitulo(), l.getAnioPublicacion());
    }

}
