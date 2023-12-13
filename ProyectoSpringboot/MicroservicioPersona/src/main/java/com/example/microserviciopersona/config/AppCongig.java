package com.example.microserviciopersona.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Esta clase al tener @Configuration nos permite crear objetos (componentes de spring)
 * y los podemos registrar en el contenedor o proyecto con @Bean
 * */

@Configuration
public class AppCongig {

    @Bean("clienteRest")
    public RestTemplate registrarRestTemplate(){
        return new RestTemplate();
    }
}
