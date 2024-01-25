package com.project.cookshare.services;

import com.project.cookshare.models.User;
import com.project.cookshare.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User addUser(User user);
    Optional<User> getUserById(Integer id);
    List<User> getAllUsers();
    User updateUser(User user);
    void deleteUserById(Integer id);
}
