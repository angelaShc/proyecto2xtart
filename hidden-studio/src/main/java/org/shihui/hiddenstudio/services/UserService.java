package org.shihui.hiddenstudio.services;

import org.shihui.hiddenstudio.DTOs.UserDTO;
import org.shihui.hiddenstudio.entities.User;
import org.shihui.hiddenstudio.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<UserDTO> listarTodoUser() {
        return userRepository.findAll().stream()
                .map(UserDTO::getUserDTO)
                .toList();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("No hay usuario con nombre: " + username));
    }

    public User crearUsuario(User user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        List<String> rolesEntrada = user.getRole();

        if (rolesEntrada == null || rolesEntrada.isEmpty()) {
            user.setRole(List.of("ROLE_USER"));
        } else {
            List<String> rolesFormateados = user.getRole().stream()
                    .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
                    .toList();
            user.setRole(rolesFormateados);
        }

        return userRepository.save(user);
    }

    public User actualizarUsuario(User user) {
        return userRepository.save(user);
    }

    public void eliminarUsuario(String id) {
        userRepository.deleteById(id);
    }

    //personalizado
    public User addFriend(String userId, String friendId) {
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new RuntimeException("no existe ese usuario"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("no existe ese usuario"));

        if (user.getFriends().stream().noneMatch(u -> u.getId().equals(friendId))) {
            user.getFriends().add(friend);
            friend.getFriends().add(user);

            userRepository.save(friend);
            return userRepository.save(user);
        }

        return user;
    }

    public User removeFriend(String userId, String friendId) {

        User user =  userRepository.findById(friendId)
                .orElseThrow(() -> new RuntimeException("no existe ese usuario"));
        User friend = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("no existe ese usuario"));

        if (user.getFriends() != null) {
            user.getFriends().removeIf(u -> u.getId().equals(friendId));
        }
        if (friend.getFriends() != null) {
            friend.getFriends().removeIf(u -> u.getId().equals(userId));
        }

        userRepository.save(friend);
        return userRepository.save(user);

    }

}
