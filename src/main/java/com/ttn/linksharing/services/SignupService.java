package com.ttn.linksharing.services;

import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.exceptions.UserAlreadyExistsException;
import com.ttn.linksharing.repositories.UserRepository;
import com.ttn.linksharing.utils.CryptoUtils;
import com.ttn.linksharing.utils.EmailServiceUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignupService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailServiceUtil emailService;

    public User createUser(User user) {
        if(userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())){
            throw new UserAlreadyExistsException("User Already Exists!");
        }
        user.setPassword(CryptoUtils.encrypt(user.getPassword()));

//        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//        User user1 = userRepository.save(user);
//        session.getTransaction().commit();
//        session.close();
        User savedUser = userRepository.save(user);
        if(savedUser != null){
            emailService.sendMail(
                    savedUser.getEmail(),
                    "Account Activation",
                    "Welcome to link sharing @ TO THE NEW"
            );
        }
        return savedUser;
    }

}
