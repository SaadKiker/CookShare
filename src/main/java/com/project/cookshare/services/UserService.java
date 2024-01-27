package com.project.cookshare.services;

import com.project.cookshare.DTOs.*;

public interface UserService {

    // Class Diagram Methods
    void registerUser(String userName, String password);
    void updateProfile(UserDTO userDTO);
    void deleteUser(Integer userId);

    // Additional Methods
    UserDTO findUserByUsername(String username);

}