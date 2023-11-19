package com.enpm613.algolab.service;

import com.enpm613.algolab.entity.user.User;
import com.enpm613.algolab.repository.UserRepository;
import com.enpm613.algolab.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    Validator validator;

    @Autowired
    UserRepository userRepository;

    public User registerUser(User user) {
        if (!validator.validateUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists.");
        }

        if (!validator.validateEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        if (!validator.validatePassword(user.getPassword())) {
            throw new RuntimeException("Password is not strong enough.");
        }
        return userRepository.save(user);
    }

    public User updateUser(User updatedUser) {
        User existingUser = userRepository.findById(updatedUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found."));

        // Check if the updated username is valid and not taken by another user
        if (!existingUser.getUsername().equals(updatedUser.getUsername()) &&
                !validator.validateUsername(updatedUser.getUsername())) {
            throw new RuntimeException("Username already exists.");
        }

        if (!validator.validatePassword(updatedUser.getPassword())) {
            throw new RuntimeException("Password is not strong enough.");
        }

        return userRepository.save(updatedUser);
    }

    public void deleteUser(String userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        userRepository.delete(existingUser);
    }

    public boolean isValidUsername(String username) {
        return validator.validateUsername(username);
    }
}
