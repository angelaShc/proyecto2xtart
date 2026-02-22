package org.shihui.hiddenstudio.repositories;

import org.shihui.hiddenstudio.entities.GameProgress;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameProgressRepository extends MongoRepository<GameProgress,String> {

    Optional<GameProgress> findByUsuarioIdAndJuegoId(String usuarioId, String juegoId);
    List<GameProgress> findByUsuarioId(String usuarioId);
    List<GameProgress> findByJuegoId(String juegoId);

}
