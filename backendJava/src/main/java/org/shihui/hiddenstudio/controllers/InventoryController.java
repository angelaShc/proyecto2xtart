package org.shihui.hiddenstudio.controllers;

import org.shihui.hiddenstudio.entities.Inventory;
import org.shihui.hiddenstudio.entities.User;
import org.shihui.hiddenstudio.services.InventoryService;
import org.shihui.hiddenstudio.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inventory")
@CrossOrigin(origins = "http://localhost:4200")
public class InventoryController {

    private final InventoryService inventoryService;
    private final UserService userService;

    public InventoryController(InventoryService inventoryService, UserService userService) {
        this.inventoryService = inventoryService;
        this.userService = userService;
    }

    //segun permiso
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Inventory>> getAll() {
        return ResponseEntity.ok(inventoryService.listarTodo());
    }

    @GetMapping
    public ResponseEntity<List<Inventory>> getMyInventory(Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        return ResponseEntity.ok(inventoryService.getInventoryByUserId(user.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getById(@PathVariable("id") String id) {
        return inventoryService.getInventoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Inventory> create(Authentication authentication, @RequestBody Inventory inventory) {
        User user = userService.findByUsername(authentication.getName());
        inventory.setUsuarioId(user.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.crearInventory(inventory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inventory> update(@PathVariable("id") String id, @RequestBody Inventory inventory) {
        inventory.setId(id);
        return ResponseEntity.ok(inventoryService.actualizarInventory(inventory));
    }

    //segun permiso
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        Optional<Inventory> inventory = inventoryService.getInventoryById(id);
        if (inventory.isPresent()) {
            inventoryService.eliminarInventario(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.noContent().build();
    }

}
