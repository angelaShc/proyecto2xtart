package org.shihui.hiddenstudio.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "inventory")
public class Inventory {

    @Id
    private String id;

    @Field(name = "juego_id")
    private String juegoId;

    private String nombre;
    private String detalle;

    @Field(name = "usuario_id")
    private String usuarioId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJuegoId() {
        return juegoId;
    }

    public void setJuegoId(String juegoId) {
        this.juegoId = juegoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDetalle() { return detalle; }

    public void setDetalle(String detalle) { this.detalle = detalle; }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id='" + id + '\'' +
                ", juegoId='" + juegoId + '\'' +
                ", tipo='" + nombre + '\'' +
                ", detalle='" + detalle + '\'' +
                ", usuarioId='" + usuarioId + '\'' +
                '}';
    }
}
