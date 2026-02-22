package org.shihui.hiddenstudio.services;

import org.shihui.hiddenstudio.entities.Inventory;
import org.shihui.hiddenstudio.repositories.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;
import java.util.Optional;

@Service
@ApplicationScope
public class InventoryService {

    private InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<Inventory> listarTodo() {
        return inventoryRepository.findAll();
    }

    public Optional<Inventory> getInventoryById(String id) {
        return inventoryRepository.findById(id);
    }

    public List<Inventory> getInventoryByUserId(String userId) {
        return inventoryRepository.findByUsuarioId(userId);
    }

    public Inventory crearInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public Inventory actualizarInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public void eliminarInventario(String id) {
        inventoryRepository.deleteById(id);
    }

}
