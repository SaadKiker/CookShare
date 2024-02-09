package com.project.cookshare.services;

import com.project.cookshare.DTOs.*;

public interface UserService {

    // Class Diagram Methods
    void registerUser(String name, String userName, String password);
    boolean login(String userName, String password);
    void updateProfile(UserDTO userDTO);
    void deleteAccount(Integer userId);

    // Additional Methods
    UserDTO findUserByUsername(String username);
    int calculateRecipesSubmittedByUser(int userId);
}