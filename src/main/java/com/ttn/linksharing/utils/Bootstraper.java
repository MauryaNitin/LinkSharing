package com.ttn.linksharing.utils;

import com.ttn.linksharing.CO.TopicCO;
import com.ttn.linksharing.entities.Subscription;
import com.ttn.linksharing.entities.Topic;
import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.enums.Roles;
import com.ttn.linksharing.enums.Visibility;
import com.ttn.linksharing.repositories.TopicRepository;
import com.ttn.linksharing.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Bootstraper {
    @Autowired
    UserService userService;
    @Autowired
    TopicRepository topicRepository;

//    @EventListener(ApplicationStartedEvent.class)
    public void bootstrapData(){
        User user = new User();
        user.setFirstname("Admin");
        user.setLastname("admin");
        user.setEmail("admin@gmail.com");
        user.setUsername("admin19");
        user.setPassword(CryptoUtils.encrypt("admin"));
        user.setRole(Roles.ADMIN);
        user.setProfilePicturePath("p2.png");
        userService.createUser(user);

        User user1 = new User();
        user1.setFirstname("Nitin");
        user1.setLastname("Maurya");
        user1.setEmail("nitin@gmail.com");
        user1.setUsername("n97");
        user1.setPassword(CryptoUtils.encrypt("nitin"));
        user1.setRole(Roles.USER);
        user1.setProfilePicturePath("p1.png");
        userService.createUser(user1);

        Topic topic1 = new Topic();
        topic1.setName("Java");
        topic1.setUser(user1);
        topic1.setVisibility(Visibility.PUBLIC);
        Subscription subscription1 = new Subscription();
        subscription1.setUser(user1);
        subscription1.setTopic(topic1);
        topic1.setSubscriptions(Arrays.asList(subscription1));
        topicRepository.save(topic1);

        Topic topic2 = new Topic();
        topic2.setName("Spring");
        topic2.setUser(user1);
        topic2.setVisibility(Visibility.PUBLIC);
        Subscription subscription2 = new Subscription();
        subscription2.setUser(user1);
        subscription2.setTopic(topic2);
        topic2.setSubscriptions(Arrays.asList(subscription2));
        topicRepository.save(topic2);

        Topic topic3 = new Topic();
        topic3.setName("Hibernate");
        topic3.setUser(user1);
        topic3.setVisibility(Visibility.PUBLIC);
        Subscription subscription3 = new Subscription();
        subscription3.setUser(user1);
        subscription3.setTopic(topic3);
        topic3.setSubscriptions(Arrays.asList(subscription3));
        topicRepository.save(topic3);

        Topic topic4 = new Topic();
        topic4.setName("Grails");
        topic4.setUser(user);
        topic4.setVisibility(Visibility.PRIVATE);
        Subscription subscription4 = new Subscription();
        subscription4.setUser(user);
        subscription4.setTopic(topic4);
        topic4.setSubscriptions(Arrays.asList(subscription4));
        topicRepository.save(topic4);

    }
}
