package org.shihui.hiddenstudio.controllers;

import org.shihui.hiddenstudio.entities.Game;
import org.shihui.hiddenstudio.services.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
@CrossOrigin(origins = "http://localhost:4200")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public ResponseEntity<List<Game>> getGames(){
        List<Game> games = gameService.listarTodo();
        return ResponseEntity.ok().body(games);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<Game> getGameById(@PathVariable("gameId") String gameId){
        return gameService.getGameById(gameId)
                .map(game -> ResponseEntity.ok().body(game))
                .orElse(ResponseEntity.notFound().build());
    }

    //segun permiso
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Game createGame(@RequestBody Game game){
        return gameService.crearGame(game);
    }

    //segun permiso
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Game updateGame(@RequestBody Game game){
        return gameService.actualizarGame(game);
    }

    //segun permiso
    @DeleteMapping("/delete/{gameId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteGame(@PathVariable("gameId") String gameId){
        gameService.eliminarGame(gameId);
    }

}
