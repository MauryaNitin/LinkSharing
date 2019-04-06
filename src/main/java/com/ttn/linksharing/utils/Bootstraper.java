package com.ttn.linksharing.utils;

import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.enums.Roles;
import com.ttn.linksharing.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Bootstraper {
    @Autowired
    UserService userService;

    @EventListener(ApplicationStartedEvent.class)
    public void bootstrapData(){
        User user = new User();
        user.setFirstname("Admin");
        user.setLastname("admin");
        user.setEmail("admin@gmail.com");
        user.setUsername("admin19");
        user.setPassword(CryptoUtils.encrypt("admin"));
        user.setRole(Roles.ADMIN);
        userService.createUser(user);

        User user1 = new User();
        user1.setFirstname("Nitin");
        user1.setLastname("Maurya");
        user1.setEmail("nitin@gmail.com");
        user1.setUsername("n97");
        user1.setPassword(CryptoUtils.encrypt("nitin"));
        user1.setRole(Roles.USER);
        userService.createUser(user1);
    }
}
