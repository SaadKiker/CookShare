package com.project.cookshare.services.implementations;

import com.project.cookshare.models.User;
import com.project.cookshare.repositories.UserRepository;
import com.project.cookshare.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

//@Service
//public class UserServiceImplementation implements UserService {
//
//    private final UserRepository userRepository;
//
//    // Using constructor injection
//    @Autowired
//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    public UserServiceImplementation(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public User addUser(User user) {
//        // Implementation
//    }
//
//    @Override
//    public Optional<User> getUserById(Integer id) {
//        // Implementation
//    }
//
//    @Override
//    public List<User> getAllUsers() {
//        // Implementation
//    }
//
//    @Override
//    public User updateUser(User user) {
//        // Implementation
//    }
//
//    @Override
//    public void deleteUserById(Integer id) {
//        // Implementation
//    }
//}
