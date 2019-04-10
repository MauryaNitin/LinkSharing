package com.ttn.linksharing.services;

import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.exceptions.IncorrectPasswordException;
import com.ttn.linksharing.exceptions.UserNotFoundException;
import com.ttn.linksharing.repositories.UserRepository;
import com.ttn.linksharing.utils.CryptoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(LoginService.class);

    public User loginUser(String username, String password) {
        // sending same parameter two times because it can have either email or username
        User user = userRepository.findByUsernameOrEmail(username, username);
        if (user == null) {
            logger.error("User with Username: " + username +  " Not found!");
            throw new UserNotFoundException("No such user exists!");
        } else if (CryptoUtils.decrypt(user.getPassword()).equals(password)) {
            return user;
        } else {
            logger.warn("Incorrect password for username: " + username);
            throw new IncorrectPasswordException("Password is incorrect!");
        }
    }
}
