package com.project.cookshare.services.implementations;

import com.project.cookshare.DTOs.*;
import com.project.cookshare.models.User;
import com.project.cookshare.repositories.RecipeRepository;
import com.project.cookshare.repositories.UserRepository;
import com.project.cookshare.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.project.cookshare.mapper.UserMapper.mapToUserDTO;
import static com.project.cookshare.mapper.UserMapper.mapToUserEntity;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository, RecipeRepository recipeRepository) {
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
    }

    // Class Diagram Methods
    @Override
    public void registerUser(String name, String username, String password) {
        User user = new User();
        user.setName(name);
        user.setUsername(username);
        // TODO: Encrypt the password before saving
        user.setPassword(password);
        userRepository.save(user);
    }

    @Override
    public boolean registerUser(UserDTO userDTO) {
        boolean result = true;
        User user = new User();
        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        // TODO: Encrypt the password before saving
        user.setPassword(userDTO.getPassword());
        user.setRecipesSubmitted(0);
        userRepository.save(user);
        return result;
    }


    @Override
    public int calculateRecipesSubmittedByUser(int userId) {
        return recipeRepository.countByAuthorId(userId);
    }

    @Override
    public boolean login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return user.getPassword().equals(password);
    }


    @Override
    public void deleteAccount(Integer userId) {
        userRepository.deleteById(userId);
        // TODO: Handle the case where the userId is not found
    }

    @Override
    public void updateProfile(UserDTO userDTO) {
        User user = mapToUserEntity(userDTO);
        userRepository.save(user);
    }

    // Additional Methods
    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
