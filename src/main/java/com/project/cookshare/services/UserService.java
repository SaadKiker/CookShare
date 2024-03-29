package com.project.cookshare.services;

import com.project.cookshare.DTOs.*;
import com.project.cookshare.models.User;

public interface UserService {

    void registerUser(UserDTO userDTO);
    User updateProfile(User user);

    User findUserByUsername(String username);
    User findUserById(Integer userId);
    int calculateRecipesSubmittedByUser(int userId);

}