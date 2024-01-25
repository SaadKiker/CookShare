package com.project.cookshare.services;

import com.project.cookshare.DTOs.UserDTO;
import com.project.cookshare.models.User;

public interface UserService {
    void registerUser(String userName, String password);
    UserDTO findUserByUsername(String username);
    void saveUser(UserDTO userDTO);
    void deleteUser(Integer userId);
}
