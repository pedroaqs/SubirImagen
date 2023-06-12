package com.example.hellowordsem9.models;

import java.util.List;

public class Publicacion {
    private String id;
    private String imagen;
    private String descripcion;
    private List<String> comentarios;

    public Publicacion(){};

    public Publicacion(String id, String imagen, String descripcion, List<String> comentarios) {
        this.id = id;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.comentarios = comentarios;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<String> getComentarios() {
        if (comentarios.size()> 0) {
            return comentarios;
        } else {
            return comentarios;
        }
    }

    public void setComentarios(List<String> comentarios) {
        this.comentarios = comentarios;
    }
}
