package org.shihui.hiddenstudio.DTOs;

import org.shihui.hiddenstudio.entities.User;

import java.util.List;

public record UserDTO(
        String id,
        String username,
        String email,
        List<String> roles,
        List<User> friends
) {
    public static UserDTO getUserDTO(User user){
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getFriends()
        );
    }
}
