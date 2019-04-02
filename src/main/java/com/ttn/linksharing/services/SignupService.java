package com.ttn.linksharing.services;

import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.exceptions.UserAlreadyExistsException;
import com.ttn.linksharing.repositories.UserRepository;
import com.ttn.linksharing.utils.CryptoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignupService {
    @Autowired
    UserRepository userRepository;

    public User createUser(User user) {
        if(userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())){
            throw new UserAlreadyExistsException("User Already Exists!");
        }
        user.setPassword(CryptoUtils.encrypt(user.getPassword()));
        return userRepository.save(user);
    }

}
