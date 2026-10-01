package com.hcl.VenueVista.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hcl.VenueVista.model.User;
import com.hcl.VenueVista.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public User getUserById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Create a new user
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Update an existing user
    public User updateUser(Long id, User user) {
        user.setId(id);
        return userRepository.save(user);
    }

    // Delete a user
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}