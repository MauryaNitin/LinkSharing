package com.ttn.linksharing.services;

import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.exceptions.UserAlreadyExistsException;
import com.ttn.linksharing.exceptions.UserNotFoundException;
import com.ttn.linksharing.repositories.UserRepository;
import com.ttn.linksharing.utils.CryptoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class SignupService {
    @Autowired
    UserRepository userRepository;

    public User createUser(User user) {
        if(userRepository.findByEmail(user.getEmail()) != null){
            throw new UserAlreadyExistsException("User Already Exists!");
        }
        try {
            user.setPassword(CryptoUtils.encrypt(user.getPassword()));
        } catch (Exception e) {
            user.setPassword(user.getPassword());
        }
        return userRepository.save(user);
    }

    public User getUserById(Long id){
        Optional<User> optional = userRepository.findById(id);
        User user = optional.isPresent() ? optional.get() : null;
        if(user == null){
            throw new UserNotFoundException("No such User Exists!");
        }
        return user;
    }

}
