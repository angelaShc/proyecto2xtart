package org.shihui.hiddenstudio.services;

import org.shihui.hiddenstudio.entities.GameProgress;
import org.shihui.hiddenstudio.repositories.GameProgressRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameProgressService {

    private final GameProgressRepository gameProgressRepository;

    public GameProgressService(GameProgressRepository gameProgressRepository) {
        this.gameProgressRepository = gameProgressRepository;
    }

    //almacen temporal: user&gameId - hora_inicio
    private final Map<String, LocalDateTime> sessionCache = new ConcurrentHashMap<>();

    //para sesion, inicio
    public void iniciarSesion(String usuarioId, String juegoId) {
        sessionCache.put(usuarioId + "-" + juegoId, LocalDateTime.now());
    }
    //fin
    public GameProgress registrarAcceso(String usuarioId, String juegoId, double progresoSesion, int puntosSesion) {
        String key = usuarioId + "-" + juegoId;
        LocalDateTime inicio = sessionCache.get(key);

        double tiempoCalculado = 0.0;
        if (inicio != null) {
            tiempoCalculado = Duration.between(inicio, LocalDateTime.now()).getSeconds();
            sessionCache.put(key, LocalDateTime.now());
        }
        GameProgress gameProgress = gameProgressRepository.findByUsuarioIdAndJuegoId(usuarioId, juegoId)
                .orElseGet(() -> {
                    GameProgress nuevo = new GameProgress();
                    nuevo.setUsuarioId(usuarioId);
                    nuevo.setJuegoId(juegoId);
                    nuevo.setAccesos(0);
                    nuevo.setTiempoJugado(0.0);
                    nuevo.setPuntosMax(0);
                    nuevo.setProgresoMax(0.0);
                    return nuevo;
                });

        if (gameProgress.getProgresoMax() == null) gameProgress.setProgresoMax(0.0);
        if (gameProgress.getPuntosMax() == null) gameProgress.setPuntosMax(0);
        if (gameProgress.getTiempoJugado() == null) gameProgress.setTiempoJugado(0.0);
        if (gameProgress.getAccesos() == null) gameProgress.setAccesos(0);

        // LÓGICA DE PERSISTENCIA Y ACTUALIZACIÓN
        gameProgress.setAccesos(gameProgress.getAccesos() + 1);
        gameProgress.setTiempoJugado(gameProgress.getTiempoJugado() + tiempoCalculado);
        gameProgress.setUltimaFecha(LocalDateTime.now());

        if (puntosSesion > gameProgress.getPuntosMax()) {
            gameProgress.setPuntosMax(puntosSesion);
        }

        if (progresoSesion > gameProgress.getProgresoMax()) {
            gameProgress.setProgresoMax(progresoSesion);
        }

        return gameProgressRepository.save(gameProgress);
    }

    public void finalizarSesion(String usuarioId, String juegoId) {
        sessionCache.remove(usuarioId + "-" + juegoId);
    }

    public List<GameProgress> listarTodo() {
        return gameProgressRepository.findAll();
    }

    public Optional<GameProgress> getGameById(String id) {
        return gameProgressRepository.findById(id);
    }

    public GameProgress actualizarGameProgress(GameProgress gameProgress) {
        return gameProgressRepository.save(gameProgress);
    }

    public void eliminarGame(String id) {
        gameProgressRepository.deleteById(id);
    }

    //personalizadas

    public Double getTiempoTotalUsuario(String usuarioId) {
        return gameProgressRepository.findByUsuarioId(usuarioId).stream()
                .mapToDouble(GameProgress::getTiempoJugado)
                .sum();
    }

    public int getAccesosTotalesUsuario(String usuarioId) {
        return gameProgressRepository.findByUsuarioId(usuarioId).stream()
                .mapToInt(GameProgress::getAccesos)
                .sum();
    }

    public List<GameProgress> getProgresoPorIdUsuario(String usuarioId) {
        return gameProgressRepository.findByUsuarioId(usuarioId);
    }

}
