package org.openapitools.api;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.openapitools.exceptions.CustomServiceException;
import org.openapitools.model.Contenido;
import org.openapitools.services.ContenidoDBService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.constraints.*;
import javax.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-10-25T20:23:40.033986300+02:00[Europe/Madrid]", comments = "Generator version: 7.9.0")
@Controller
@RequestMapping("${openapi.aPIContenidos.base-path:/StreamHub}")
public class ContenidosApiController implements ContenidosApi {
    private final ContenidoDBService contenidoDBService;
    private final NativeWebRequest request;

    @Autowired
    public ContenidosApiController(ContenidoDBService contenidoDBService, NativeWebRequest request) {
        this.contenidoDBService = contenidoDBService;
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    //Devolver todos los contenidos
    @Override
    public ResponseEntity<List<Contenido>> contenidosGet() {
        List<Contenido> contenidosList = contenidoDBService.getAllContenidos();
        return ResponseEntity.ok(contenidosList);
    }

    @Override
    public ResponseEntity<List<Contenido>> contenidosEtiquetasGet(List<Integer> etiquetas) {
        List<Contenido> contenidos = contenidoDBService.getContenidoByEtiquetaIds(etiquetas);

        if (contenidos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 si no hay contenidos
        }

        return new ResponseEntity<>(contenidos, HttpStatus.OK); // 200 y la lista de contenidos
    }

        //Eliminar un contenido pasado su id
    @Override
    public ResponseEntity<Void> contenidosIdDeContenidoDelete(Integer idDeContenido) {
        boolean borrado = contenidoDBService.deleteContenidoById(idDeContenido);
        if (borrado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Devolver un contenido por su id
    @Override
    public ResponseEntity<Contenido> contenidosIdDeContenidoGet(Integer idDeContenido) {
        Optional<Contenido> contenidoOptional = contenidoDBService.getContenidoById(idDeContenido);
        if (contenidoOptional.isPresent()) {
            return ResponseEntity.ok(contenidoOptional.get());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //Actualizar un contenido pasado su id
    @Override
    public ResponseEntity<Void> contenidosIdDeContenidoPut(Integer idDeContenido, Contenido contenido) {
        boolean actualizado = false;
        try{
            actualizado = contenidoDBService.putContenido(idDeContenido, contenido);
        }catch(CustomServiceException e){
            e.printStackTrace();
            //Lanzamos bad request(400) si se ha intentado acutalizar un obtenido y se ha
            //incumplido la integridad referencial
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (actualizado) {
            //Si se ha actualizado correctamente devolvemos un NO_CONTENT
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            //Si no se ha encontrado el contenido devolvemos un NOT_FOUND
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Crear un contenido
    @Override
    public ResponseEntity<Void> contenidosPost(Contenido contenido) {
        try {
            contenidoDBService.postContenido(contenido);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (CustomServiceException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //Devolver los contenidos de un tipo concreto
    @Override
    public ResponseEntity<List<Contenido>> contenidosTipoTipoDeContenidoGet(String tipoDeContenido) {
        List<Contenido> contenidosList = contenidoDBService.getContenidosByTipo(tipoDeContenido);
        return ResponseEntity.ok(contenidosList);
    }

    //Devolver la url de un contenido por su id
    @Override
    public ResponseEntity<String> contenidosVisualizarIdDeContenidoGet(Integer idDeContenido) {
        String url = contenidoDBService.visualizarContenidoById(idDeContenido);
        if(url != null)
            return  ResponseEntity.ok(url);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
