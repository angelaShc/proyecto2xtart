package org.shihui.hiddenstudio.controllers;

import org.shihui.hiddenstudio.DTOs.GameProgressDTO;
import org.shihui.hiddenstudio.entities.GameProgress;
import org.shihui.hiddenstudio.entities.User;
import org.shihui.hiddenstudio.services.GameProgressService;
import org.shihui.hiddenstudio.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/me/progress")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class GameProgressController {

    private final GameProgressService gameProgressService;
    private final UserService userService;

    public GameProgressController(GameProgressService gameProgressService, UserService userService) {
        this.gameProgressService = gameProgressService;
        this.userService = userService;
    }

    //llamada desde el juego
    @PostMapping("/start/{juegoId}")
    public ResponseEntity<Void> iniciar(Authentication auth, @PathVariable("juegoId") String juegoId) {
        User user = userService.findByUsername(auth.getName());
        gameProgressService.iniciarSesion(user.getId(), juegoId);
        return ResponseEntity.ok().build();
    }
    //desde unity
    @PostMapping("/registrar")
    public ResponseEntity<GameProgress> registrar(
            Authentication auth,
            @RequestParam String juegoId,
            @RequestParam(defaultValue = "0") int puntos,
            @RequestParam(defaultValue = "0") int progreso) {
        User user = userService.findByUsername(auth.getName());
        GameProgress actualizado = gameProgressService.registrarAcceso(user.getId(), juegoId, progreso, puntos);
        return ResponseEntity.ok(actualizado);
    }

    //personalizado

    @GetMapping
    public List<GameProgressDTO> getMyProgress(Authentication auth) {
        User user = userService.findByUsername(auth.getName());
        return gameProgressService.getProgresoPorIdUsuario(user.getId())
                .stream()
                .map(GameProgressDTO::getGameProgressDTO)
                .toList();
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getMyStats(Authentication auth) {
        User user = userService.findByUsername(auth.getName());
        Map<String, Object> stats = new HashMap<>();
        stats.put("tiempoTotal", gameProgressService.getTiempoTotalUsuario(user.getId()));
        stats.put("accesosTotales", gameProgressService.getAccesosTotalesUsuario(user.getId()));

        return ResponseEntity.ok(stats);
    }

    //segun permiso
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable("id") String id) {
        gameProgressService.eliminarGame(id);
        return ResponseEntity.noContent().build();
    }

}
