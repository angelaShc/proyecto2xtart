package org.shihui.hiddenstudio.repositories;

import org.shihui.hiddenstudio.entities.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends MongoRepository<Inventory,String> {
    List<Inventory> findByUsuarioId(String userId);
}
