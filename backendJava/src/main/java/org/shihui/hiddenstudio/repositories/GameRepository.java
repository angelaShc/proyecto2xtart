package org.shihui.hiddenstudio.repositories;

import org.bson.types.ObjectId;
import org.shihui.hiddenstudio.entities.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends MongoRepository<Game, String> {
}
