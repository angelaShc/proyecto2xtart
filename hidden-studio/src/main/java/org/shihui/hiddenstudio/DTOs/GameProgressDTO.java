package org.shihui.hiddenstudio.DTOs;

import org.shihui.hiddenstudio.entities.GameProgress;

public record GameProgressDTO(

    String juegoId,
    int accesos,
    Double tiempoJugado,
    Double progreso,
    int puntos

){

    public static GameProgressDTO getGameProgressDTO(GameProgress gameProgress){
        return new GameProgressDTO(
                gameProgress.getJuegoId(),
                gameProgress.getAccesos(),
                gameProgress.getTiempoJugado(),
                gameProgress.getProgresoMax(),
                gameProgress.getPuntosMax()
        );
    }

}

