package com.project.cookshare.services;

import com.project.cookshare.DTOs.*;
import com.project.cookshare.models.User;

public interface UserService {

    // Class Diagram Methods
    void registerUser(String name, String userName, String password);
    boolean registerUser(UserDTO userDTO);
    boolean login(String userName, String password);
    void updateProfile(UserDTO userDTO);
    void deleteAccount(Integer userId);

    // Additional Methods
    User findUserByUsername(String username);
    int calculateRecipesSubmittedByUser(int userId);
}