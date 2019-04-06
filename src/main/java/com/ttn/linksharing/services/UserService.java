package com.ttn.linksharing.services;

import com.ttn.linksharing.DTO.UserDTO;
import com.ttn.linksharing.entities.Subscription;
import com.ttn.linksharing.entities.Topic;
import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.exceptions.UserNotFoundException;
import com.ttn.linksharing.repositories.SubscriptionRepository;
import com.ttn.linksharing.repositories.TopicRepository;
import com.ttn.linksharing.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    TopicRepository topicRepository;


    public User createUser(User user){
        return userRepository.save(user);
    }

    public Boolean findByUsernameOrEmail(User user){
        return  userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail());
    }

    public User getUserById(Long id){
        Optional<User> optional = userRepository.findById(id);
        User user = optional.isPresent() ? optional.get() : null;
        if(user == null){
            throw new UserNotFoundException("No such User Exists!");
        }
        return user;
    }

    public UserDTO getUserDto(Long userId){
        UserDTO userDTO = new UserDTO(getUserById(userId));

//        userDTO.setSubscriptions(getSubscriptionsCount(userId));
//        userDTO.setTopics(topicRepository.get());
        return userDTO;
    }
//
//    public List<Subscription> getSubscriptions(Long userId){
//        if(userId == null){
//            throw new UserNotFoundException("No such User Exists!");
//        }
//        return subscriptionRepository.getSubscriptionByUserId(userId);
//    }
//
//    public Integer getSubscriptionsCount(Long userId){
//        if(userId == null){
//            throw new UserNotFoundException("No such User Exists!");
//        }
//    }

}
