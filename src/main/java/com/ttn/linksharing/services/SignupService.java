package com.ttn.linksharing.services;

import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.exceptions.UserAlreadyExistsException;
import com.ttn.linksharing.repositories.UserRepository;
import com.ttn.linksharing.utils.CryptoUtils;
import com.ttn.linksharing.utils.EmailServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignupService {
    @Autowired
    EmailServiceUtil emailService;

    @Autowired
    UserService userService;

    public User createUser(User user) {
        if (userService.findByUsernameOrEmail(user)) {
            throw new UserAlreadyExistsException("User Already Exists!");
        }
        user.setPassword(CryptoUtils.encrypt(user.getPassword()));
//        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//        User user1 = userRepository.save(user);
//        session.getTransaction().commit();
//        session.close();
        User savedUser = userService.createUser(user);
        if (savedUser != null) {
            new Thread(() -> emailService.sendMail(
                    savedUser.getEmail(),
                    "Account Activation",
                    "Welcome to Link Sharing @ TO THE NEW"
            )).start();
        }
        return savedUser;
    }

}
