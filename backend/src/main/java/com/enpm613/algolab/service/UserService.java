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

    public boolean isValidUsername(String username) {
        return validator.validateUsername(username);
    }
}
