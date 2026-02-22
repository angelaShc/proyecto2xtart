package org.shihui.hiddenstudio.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document (collection = "games")
public class Game {

    @Id
    private String id;

    private String descripcion;
    private String genero;
    private String titulo;
    private String urlPortada;
    private String urlDescarga;
    private String urlWebGL;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUrlPortada() {
        return urlPortada;
    }

    public void setUrlPortada(String urlPortada) {
        this.urlPortada = urlPortada;
    }

    public String getUrlDescarga() {
        return urlDescarga;
    }

    public void setUrlDescarga(String urlDescarga) {
        this.urlDescarga = urlDescarga;
    }

    public String getUrlWebGL() {
        return urlWebGL;
    }

    public void setUrlWebGL(String urlWebGL) {
        this.urlWebGL = urlWebGL;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id='" + id + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", genero='" + genero + '\'' +
                ", titulo='" + titulo + '\'' +
                ", urlPortada='" + urlPortada + '\'' +
                ", urlDescarga='" + urlDescarga + '\'' +
                ", urlWebGL='" + urlWebGL + '\'' +
                '}';
    }
}
