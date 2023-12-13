package com.example.microserviciopersona.services;

import com.example.microserviciopersona.dtos.DtoPersona;
import com.example.microserviciopersona.entitys.Persona;
import com.example.microserviciopersona.repositorys.RepositoryPersona;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServicePersona {

    @Autowired
    private RepositoryPersona repositoryPersona;

    @Autowired
    private RestTemplate template;

    @Transactional
    public DtoPersona getById(@PathVariable int id) {

        Optional<Persona> aux = repositoryPersona.findById(id);

        if (aux.isPresent()) {
            Persona p = aux.get();
            return transformDTO(p);
        }

        return null;
    }

    @Transactional
    public int getIdPersona(@PathVariable int id) {

        Optional<Persona> persona = repositoryPersona.findById(id);

        if (persona.isPresent()) {
            return persona.get().getId();
        }
        return 0;
    }

    @Transactional
    public String getDatosById(@PathVariable int id) {

        Optional<Persona> aux = repositoryPersona.findById(id);

        if (aux.isPresent()) {
            Persona p = aux.get();
            return p.getApellido() + " " + p.getNombre();
        }

        return null;
    }

    @Transactional
    public List<DtoPersona> getAll() {

        List<Persona> personas = repositoryPersona.findAll();

        List<DtoPersona> dtos = personas.stream()
                .map(persona -> new DtoPersona(persona.getNombre(), persona.getApellido(), persona.getEmail()))
                .collect(Collectors.toList());

        return dtos;
    }

    @Transactional
    public DtoPersona create(@RequestBody DtoPersona p) {

        Persona nueva = new Persona(p.getNombre(), p.getApellido(), p.getEmail());
        repositoryPersona.save(nueva);
        return transformDTO(nueva);

    }

    @Transactional
    public DtoPersona update(int id, String email) {

        Optional<Persona> aux = repositoryPersona.findById(id);

        if (aux.isPresent()) {

            Persona p = aux.get();
            p.setEmail(email);

            repositoryPersona.save(p);

            return transformDTO(p);
        } else {
            return null;
        }
    }

    @Transactional
    public DtoPersona delete(int id) throws Exception {

        Optional<Persona> aux = repositoryPersona.findById(id);

        if (aux.isPresent()) {

            try {
                repositoryPersona.deleteById(id);
                return transformDTO(aux.get());
            } catch (Exception e) {
                throw new Exception();
            }

        } else return null;

    }

    @Transactional
    public List<String> getListadoPorCantidadDePaginasRevisadasPorAnio(@PathVariable int anio) throws Exception {
        List<String> revisoresInfo = new ArrayList<>();
        List<Persona> peronas = repositoryPersona.findAll();
        for (Persona p : peronas) {
            try {
                int cantDePaginasRevisadas = 0;

                String url = "http://localhost:8082/reportesCapitulos/capitulosRevisadosByAnioPorPersona/" + anio + "/" + p.getId();

                // Define un ParameterizedTypeReference para indicar que esperamos una List<Integer>
                ParameterizedTypeReference<List<Integer>> responseType = new ParameterizedTypeReference<>() {
                };

                ResponseEntity<List<Integer>> idsCapitulosRevisados = template.exchange(url, HttpMethod.GET, null, responseType);

                if (idsCapitulosRevisados.getStatusCode().is2xxSuccessful()) {

                    List<Integer> idsCapitulosRevisadosBody = idsCapitulosRevisados.getBody();
                    //de una persona
                    for (Integer idCapitulo : idsCapitulosRevisadosBody) {
                        ResponseEntity<Integer> responseCantPaginas = template.getForEntity("http://localhost:8080/capitulos/getCantPaginas/" + idCapitulo, Integer.class);

                        cantDePaginasRevisadas += responseCantPaginas.getBody();
                    }
                }
                revisoresInfo.add(p.getApellido() + " " + p.getNombre() + " revisó un total de páginas:" + cantDePaginasRevisadas + " en el año: " + anio);
            } catch (Exception e) {
                  e.printStackTrace();
            }
        }
        return revisoresInfo;
    }

    private DtoPersona transformDTO(Persona p){

        return new DtoPersona(p.getNombre(), p.getApellido(), p.getEmail());
    }
}
