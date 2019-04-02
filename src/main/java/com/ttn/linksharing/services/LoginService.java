package com.ttn.linksharing.services;

import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.exceptions.IncorrectPasswordException;
import com.ttn.linksharing.exceptions.UserNotFoundException;
import com.ttn.linksharing.repositories.UserRepository;
import com.ttn.linksharing.utils.CryptoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Service
public class LoginService {

    @Autowired
    UserRepository userRepository;

    public User loginUser(Map<String, String> credentials){
        User user = userRepository.findByUsernameOrEmail(credentials.get("username"), credentials.get("username"));
        if(user == null){
            throw new UserNotFoundException("No such user exists!");
        }
        else if(CryptoUtils.decrypt(user.getPassword()).equals(credentials.get("password"))){
            return user;
        }
        else{
            throw new IncorrectPasswordException("Password is incorrect!");
        }
    }
}
