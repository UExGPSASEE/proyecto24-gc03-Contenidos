package org.openapitools.services;

import org.openapitools.exceptions.CustomServiceException;
import org.openapitools.model.Contenido;
import org.openapitools.modelDB.ContenidoDB;
import org.openapitools.repositories.ContenidoDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContenidoDBService {
    private final ContenidoDBRepository contenidoDBRepository;

    @Autowired
    public ContenidoDBService(ContenidoDBRepository contenidoDBRepository) {
        this.contenidoDBRepository = contenidoDBRepository;
    }

    private Contenido convertToContenido(ContenidoDB contenidoDB) {
        return new Contenido(contenidoDB);
    }

    // Guardar un contenido comprobando que se cumpla la integridad referencial en el atributo perteneceA
    public void saveContenidoDB(ContenidoDB contenidoDB) {
        if (contenidoDB.getPerteneceA() != null) {
            if (!contenidoDBRepository.existsById(contenidoDB.getPerteneceA())) {
                //Si no existe , lanzamos una excepción
                throw new IllegalArgumentException("El contenido con ID " +
                        contenidoDB.getPerteneceA() + " al cual se referencia en perteneceA no existe.");
            }
        }
        //Si es nulo o si existe, se guarda el contenido
        contenidoDBRepository.save(contenidoDB);
    }

    // Borra por ID
    public boolean deleteContenidoById(Integer id) {
        Optional<ContenidoDB> contenido = contenidoDBRepository.findById(id);
        if (contenido.isPresent()) {
            contenidoDBRepository.deleteById(id);
            return true; // Devuelve verdadero si se ha borrado correctamente
        }
        return false; // Devuelve falso si no se ha encontrado el contenido
    }

    // Obtener todos los contenidos
    public List<Contenido> getAllContenidos() {
        List<ContenidoDB> listaContenidosDB = (List<ContenidoDB>) contenidoDBRepository.findAll();
        return listaContenidosDB.stream()
                .map(this::convertToContenido)
                .collect(Collectors.toList());
    }

    //devolver un contenido por ID
    public Optional<Contenido> getContenidoById(Integer id) {
        Optional<ContenidoDB> contenidoDBOptional = contenidoDBRepository.findById(id);
        return contenidoDBOptional.map(this::convertToContenido);
    }

    //Actualizar un contenido por ID
    public boolean putContenido(Integer id, Contenido contenido) {
        ContenidoDB contenidoDB = new ContenidoDB(contenido);
        Optional<ContenidoDB> existingContenido = contenidoDBRepository.findById(id);
        boolean contenidoCreado = false;
        // Si existe el contenido, se actualiza
        if (existingContenido.isPresent()) {
            ContenidoDB updatedContenidoDB = existingContenido.get();
            updatedContenidoDB.setTipo(contenidoDB.getTipo());
            updatedContenidoDB.setTitulo(contenidoDB.getTitulo());
            updatedContenidoDB.setProductionYear(contenidoDB.getProductionYear());
            updatedContenidoDB.setClasificacionEdad(contenidoDB.getClasificacionEdad());
            updatedContenidoDB.setDescripcion(contenidoDB.getDescripcion());
            updatedContenidoDB.setPerteneceA(contenidoDB.getPerteneceA());
            updatedContenidoDB.setNumeroElementos(contenidoDB.getNumeroElementos());
            updatedContenidoDB.setDuracion(contenidoDB.getDuracion());
            updatedContenidoDB.setUrl(contenidoDB.getUrl());

            //Llamamos al método saveContenidoDB para guardar el contenido actualizado comprobando la integridad referencial
            try {
                saveContenidoDB(updatedContenidoDB);
                contenidoCreado = true;
            } catch (IllegalArgumentException e) {
                throw new CustomServiceException("Error al crear contenido: " + e.getMessage(), e);
            }
        }
        return contenidoCreado;
    }

    public void  postContenido(Contenido contenido) {
        ContenidoDB contenidoDB = new ContenidoDB(contenido);
        try {
            saveContenidoDB(contenidoDB);
        } catch (IllegalArgumentException e) {
            throw new CustomServiceException("Error al crear contenido: " + e.getMessage(), e);
        }
    }

    public List<Contenido> getContenidosByTipo(String tipo) {
        List<ContenidoDB> contenidoList = new ArrayList<>();
        contenidoDBRepository.findAll().forEach(contenidoList::add);

        List<ContenidoDB> listaFiltrada = contenidoList.stream()
                .filter(contenidoDB -> contenidoDB.getTipo().equals(tipo))
                .collect(Collectors.toList());
        return listaFiltrada.stream()
                .map(this::convertToContenido)
                .collect(Collectors.toList());
    }

    public String visualizarContenidoById(Integer id) {
        Optional<ContenidoDB> contenidoDBOptional = contenidoDBRepository.findById(id);
        return contenidoDBOptional.map(ContenidoDB::getUrl).orElse(null);
    }
}


