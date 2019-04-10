package com.ttn.linksharing.services;

import com.ttn.linksharing.CO.InvitationCO;
import com.ttn.linksharing.entities.Subscription;
import com.ttn.linksharing.entities.Topic;
import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.repositories.SubscriptionRepository;
import com.ttn.linksharing.utils.EmailServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionService {
    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    UserService userService;

    @Autowired
    EmailServiceUtil emailService;

    @Autowired
    TopicService topicService;

    public Subscription createSubscription(User user, Topic topic){
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setTopic(topic);
        return subscription;
    }

    public List<Topic> getSubscribedTopicsByUserId(User user){
        return user
                .getSubscriptions()
                .stream()
                .map(Subscription::getTopic)
                .collect(Collectors.toList());
    }

    public void sendInvitation(Long userId, InvitationCO invitationCO){
        Topic topic = topicService.getTopicById(invitationCO.getTopicId());
        emailService.sendMail(
                invitationCO.getEmail(),
                "Invitation for subscribing " + topic.getName(),
                "@" + userService.getUserById(userId).getUsername()
                        + " has invited you to subscribe "
                        + topic.getName()
                        + "\n Please click this link to subscribe: ");
    }
}
