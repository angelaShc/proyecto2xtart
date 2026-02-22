package org.shihui.hiddenstudio.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document (collection = "game_progress")
public class GameProgress {

    @Id
    private String id;

    private Integer accesos;

    @Field(name = "juego_id")
    private String juegoId;

    private Double progresoMax;

    @Field(name = "tiempo_jugado")
    private Double tiempoJugado;

    @Field(name = "ultima_fecha")
    private LocalDateTime ultimaFecha;

    @Field(name = "usuario_id")
    private String usuarioId;

    private Integer puntosMax;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAccesos() {
        return accesos;
    }

    public void setAccesos(Integer accesos) {
        this.accesos = accesos;
    }

    public String getJuegoId() {
        return juegoId;
    }

    public void setJuegoId(String juegoId) {
        this.juegoId = juegoId;
    }

    public Double getProgresoMax() {
        return progresoMax;
    }

    public void setProgresoMax(Double progresoMax) {
        this.progresoMax = progresoMax;
    }

    public Double getTiempoJugado() {
        return tiempoJugado;
    }

    public void setTiempoJugado(Double tiempoJugado) {
        this.tiempoJugado = tiempoJugado;
    }

    public LocalDateTime getUltimaFecha() {
        return ultimaFecha;
    }

    public void setUltimaFecha(LocalDateTime ultimaFecha) {
        this.ultimaFecha = ultimaFecha;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getPuntosMax() {
        return puntosMax;
    }

    public void setPuntosMax(Integer puntosMax) {
        this.puntosMax = puntosMax;
    }

    @Override
    public String toString() {
        return "GameProgress{" +
                "id='" + id + '\'' +
                ", numero de accesos=" + accesos +
                ", id del juego='" + juegoId + '\'' +
                ", progreso maximo=" + progresoMax +
                ", tiempo jugado=" + tiempoJugado +
                ", ultima fecha jugado='" + ultimaFecha + '\'' +
                ", id del usuario='" + usuarioId + '\'' +
                ", puntos maximos=" + puntosMax +
                '}';
    }
}
