package com.project.cookshare.mapper;

import com.project.cookshare.DTOs.UserDTO;
import com.project.cookshare.models.User;

public class UserMapper {

    public static User mapToUserEntity(UserDTO user) {
        return User.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .password(user.getUsername())
                .build();
    }

    public static UserDTO mapToUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .password(user.getUsername())
                .build();
    }
}
