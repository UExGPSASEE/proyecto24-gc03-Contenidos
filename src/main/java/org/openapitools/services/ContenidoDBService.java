package org.openapitools.services;

import org.openapitools.repositories.ContenidoDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContenidoDBService {
    private final ContenidoDBRepository contenidoDBRepository;

    @Autowired
    public ContenidoDBService(ContenidoDBRepository contenidoDBRepository) {
        this.contenidoDBRepository = contenidoDBRepository;
    }

    //Método de prueba para probar su correcto funcionamiento. Borrar en la versión final
    public void printAllContenidos() {
        System.out.println("=======CONTENIDOS=======");
        contenidoDBRepository.findAll().forEach(System.out::println);
    }
}
