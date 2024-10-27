package org.openapitools.modelDB;

import javax.persistence.*;

@Entity
@Table(name = "Contenido")
public class ContenidoDB {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String tipo;
    private String titulo;
    private Integer production_year;

    @Column (name = "clasificacion_edad")
    private String clasificacionEdad;

    private String descripcion;

    @Column (name = "pertenece_a")
    private Integer perteneceA;

    @Column (name = "numero_elementos")
    private Integer numeroElementos;

    private Integer duracion;
    private String url;

    protected ContenidoDB() {
        // Empty constructor
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getProduction_year() {
        return production_year;
    }

    public void setProduction_year(Integer production_year) {
        this.production_year = production_year;
    }

    public String getClasificacionEdad() {
        return clasificacionEdad;
    }

    public void setClasificacionEdad(String clasificacionEdad) {
        this.clasificacionEdad = clasificacionEdad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getPerteneceA() {
        return perteneceA;
    }

    public void setPerteneceA(Integer perteneceA) {
        this.perteneceA = perteneceA;
    }

    public Integer getNumeroElementos() {
        return numeroElementos;
    }

    public void setNumeroElementos(Integer numeroElementos) {
        this.numeroElementos = numeroElementos;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ContenidoDB{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", titulo='" + titulo + '\'' +
                ", production_year=" + production_year +
                ", clasificacionEdad='" + clasificacionEdad + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", perteneceA=" + perteneceA +
                ", numeroElementos=" + numeroElementos +
                ", duracion=" + duracion +
                ", url='" + url + '\'' +
                '}';
    }
}
