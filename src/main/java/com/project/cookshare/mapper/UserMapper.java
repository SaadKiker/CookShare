package com.project.cookshare.mapper;

import com.project.cookshare.DTOs.UserDTO;
import com.project.cookshare.models.User;

public class UserMapper {

    public static User mapToUser(UserDTO user) {
        return User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getUsername())
                .build();
    }

    public static UserDTO mapToUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getUsername())
                .build();
    }
}
