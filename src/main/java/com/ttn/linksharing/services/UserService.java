package com.ttn.linksharing.services;

import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.exceptions.UserNotFoundException;
import com.ttn.linksharing.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User getUserById(Long id){
        Optional<User> optional = userRepository.findById(id);
        User user = optional.isPresent() ? optional.get() : null;
        if(user == null){
            throw new UserNotFoundException("No such User Exists!");
        }
        return user;
    }
}
