package com.df.doggoforever.service;

import com.df.doggoforever.model.User;
import com.df.doggoforever.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public User matchesUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public boolean usernameIsTaken (String username) {
        User existingUser = userRepository.findByUsername(username).orElse(null);
        return existingUser != null;
    }

}
