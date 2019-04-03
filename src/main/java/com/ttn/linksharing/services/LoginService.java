package com.ttn.linksharing.services;

import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.exceptions.IncorrectPasswordException;
import com.ttn.linksharing.exceptions.UserNotFoundException;
import com.ttn.linksharing.repositories.UserRepository;
import com.ttn.linksharing.utils.CryptoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    UserRepository userRepository;

    public User loginUser(String username, String password){
        // sending same parameter two times because it can have either email or username
        User user = userRepository.findByUsernameOrEmail(username, username);
        if(user == null){
            throw new UserNotFoundException("No such user exists!");
        }
        else if(CryptoUtils.decrypt(user.getPassword()).equals(password)){
            return user;
        }
        else{
            throw new IncorrectPasswordException("Password is incorrect!");
        }
    }
}
