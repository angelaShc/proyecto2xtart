package org.shihui.hiddenstudio.controllers;

import org.shihui.hiddenstudio.DTOs.UserDTO;
import org.shihui.hiddenstudio.entities.User;
import org.shihui.hiddenstudio.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //segun permiso
    @GetMapping("/all_users")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.listarTodoUser());
    }

    @GetMapping
    public ResponseEntity<UserDTO> getMyProfile(Authentication authentication) {
        UserDTO userDTO = UserDTO.getUserDTO(userService.findByUsername(authentication.getName()));
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/add-friend/{friendId}")
    public ResponseEntity<User> addFriend(Authentication authentication, @PathVariable("friendId") String friendId) {
        User currentUser = userService.findByUsername(authentication.getName());
        User userActualizado = userService.addFriend(currentUser.getId(), friendId);
        return ResponseEntity.ok(userActualizado);
    }

    @DeleteMapping("/remove-friend/{friendId}")
    public ResponseEntity<User> removeFriend(Authentication authentication, @PathVariable("friendId") String friendId) {
        User currentUser = userService.findByUsername(authentication.getName());
        User userActualizado = userService.removeFriend(currentUser.getId(), friendId);
        return ResponseEntity.ok(userActualizado);
    }

}
