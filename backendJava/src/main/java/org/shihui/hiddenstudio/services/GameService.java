package org.shihui.hiddenstudio.services;

import org.shihui.hiddenstudio.entities.Game;
import org.shihui.hiddenstudio.repositories.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> listarTodo() {
        return gameRepository.findAll();
    }

    public Optional<Game> getGameById(String id) {
        return gameRepository.findById(id);
    }

    public Game crearGame(Game game) {
        return gameRepository.save(game);
    }

    public Game actualizarGame(Game game) {
        return gameRepository.save(game);
    }

    public void eliminarGame(String id) {
        gameRepository.deleteById(id);
    }
}
