package com.ttn.linksharing.services;

import com.ttn.linksharing.CO.UpdatePasswordCO;
import com.ttn.linksharing.CO.UpdateProfileCO;
import com.ttn.linksharing.DTO.UserDTO;
import com.ttn.linksharing.DTO.UserPublicDTO;
import com.ttn.linksharing.controllers.FileUploadController;
import com.ttn.linksharing.entities.Topic;
import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.enums.Visibility;
import com.ttn.linksharing.exceptions.UserNotFoundException;
import com.ttn.linksharing.repositories.UserRepository;
import com.ttn.linksharing.utils.CryptoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SubscriptionService subscriptionService;

    @Autowired
    TopicService topicService;

    @Autowired
    FileUploadController uploader;


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

        userDTO.setSubscriptions(getSubscriptions(userId));
        userDTO.setTopics(getTopics(userId));
        return userDTO;
    }

    public List<Topic> getSubscriptions(Long userId){
        if(userId == null){
            throw new UserNotFoundException("No such User Exists!");
        }
        else{
            User user = getUserById(userId);
            return subscriptionService.getSubscribedTopicsByUserId(user);
        }
    }

    public List<Topic> getTopics(Long userId){
        if(userId == null){
            throw new UserNotFoundException("No such User Exists!");
        }
        return topicService.getTopicsByUserId(userId);
    }

    public UserPublicDTO getPublicUserDto(Long userId){
        User user = getUserById(userId);
        UserPublicDTO userPublicDTO = new UserPublicDTO(user);
        userPublicDTO.setSubscriptions(user.getSubscriptions());
        userPublicDTO.setResources(user.getResources().stream().filter(x -> x.getTopic().getVisibility() != Visibility.PRIVATE).collect(Collectors.toList()));
        userPublicDTO.setTopics(user.getTopics().stream().filter(x -> x.getVisibility() != Visibility.PRIVATE).collect(Collectors.toList()));
        return userPublicDTO;
    }

    public List<User> getAllUsers(Long userId){
        return userRepository.findAllUsers().stream().filter(x -> x.getId() != userId).collect(Collectors.toList());
    }

    public User updateUserDetails(UpdateProfileCO updateProfileCO, Long id){
        User user = getUserById(id);
        user.setFirstname(updateProfileCO.getFirstname());
        user.setLastname(updateProfileCO.getLastname());
        user.setUsername(updateProfileCO.getUsername());
        user.setProfilePicturePath(updateProfileCO.getPhoto().getOriginalFilename());
        if(updateProfileCO.getPhoto() != null){
            uploader.saveProfilePicture(updateProfileCO.getPhoto(), updateProfileCO.getUsername());
        }
        return userRepository.save(user);
    }

    public User updatePassword(UpdatePasswordCO updatePasswordCO, Long id){
        User user = getUserById(id);
        user.setPassword(CryptoUtils.encrypt(updatePasswordCO.getPassword()));
        return userRepository.save(user);
    }

    public User getUserByUsernameOrEmail(String username, String email){
        return userRepository.findByUsernameOrEmail(username, email);
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
