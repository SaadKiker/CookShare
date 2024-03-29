package com.project.cookshare.services.implementations;

import com.project.cookshare.DTOs.*;
import com.project.cookshare.models.User;
import com.project.cookshare.repositories.RecipeRepository;
import com.project.cookshare.repositories.UserRepository;
import com.project.cookshare.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository, RecipeRepository recipeRepository) {
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void registerUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRecipesSubmitted(0);
        userRepository.save(user);
    }


    @Override
    public int calculateRecipesSubmittedByUser(int userId) {
        return recipeRepository.countByAuthorId(userId);
    }

    @Override
    public User updateProfile(User userDTO) {
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(userDTO.getPassword());
        }

        userRepository.save(user);
        return user;
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findUserById(Integer userId) {
        return userRepository.findUserById(userId);
    }

}
