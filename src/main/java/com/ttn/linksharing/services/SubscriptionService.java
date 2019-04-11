package com.ttn.linksharing.services;

import com.ttn.linksharing.CO.InvitationCO;
import com.ttn.linksharing.entities.Invitation;
import com.ttn.linksharing.entities.Subscription;
import com.ttn.linksharing.entities.Topic;
import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.enums.Visibility;
import com.ttn.linksharing.repositories.InvitationRepository;
import com.ttn.linksharing.repositories.SubscriptionRepository;
import com.ttn.linksharing.utils.EmailServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SubscriptionService {
    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    InvitationRepository invitationRepository;

    @Autowired
    UserService userService;

    @Autowired
    EmailServiceUtil emailService;

    @Autowired
    TopicService topicService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public Subscription createSubscription(User user, Topic topic) {
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setTopic(topic);
        return subscription;
    }

    public List<Topic> getSubscribedTopicsByUserId(User user) {
        return user
                .getSubscriptions()
                .stream()
                .map(Subscription::getTopic)
                .collect(Collectors.toList());
    }

    public Boolean sendInvitation(Long senderId, InvitationCO invitationCO) {
        Topic topic = topicService.getTopicById(invitationCO.getTopicId());
        User receiver = userService.getUserByEmail(invitationCO.getEmail());
        User sender = userService.getUserById(senderId);
        if(receiver != null && sender != null && topic != null){
            try {
                Invitation invitation= new Invitation();
                invitation.setSenderId(senderId);
                invitation.setToken(UUID.randomUUID().toString());
                invitation.setReceiverId(receiver.getId());
                invitation.setTopicId(topic.getId());
                invitationRepository.save(invitation);
                new Thread(() -> emailService.sendMail(
                        receiver.getEmail(),
                        "Invitation for subscribing " + topic.getName(),
                        "@" + sender.getUsername()
                                + " has invited you to subscribe "
                                + topic.getName()
                                + "\n Please click this link to subscribe: "
                                + "http://localhost:8080/subscribe?token="
                                + invitation.getToken())
                ).start();
                return true;
            } catch (MailException ex) {
                logger.warn("Invitation mail sent failed due to Mail exception!");
                return false;
            }
        }
        return false;
    }

    public Subscription subscribeTopic(Long userId, Long topicId){
        User user = userService.getUserById(userId);
        Topic topic = topicService.getTopicById(topicId);
        Subscription subscription = new Subscription();
        subscription.setTopic(topic);
        subscription.setUser(user);
        return subscriptionRepository.save(subscription);
    }

    public Subscription subscribePublicTopic(Long userId, Long topicId){
        User user = userService.getUserById(userId);
        Topic topic = topicService.getTopicById(topicId);
        if(topic.getVisibility() != Visibility.PRIVATE){
            Subscription subscription = new Subscription();
            subscription.setTopic(topic);
            subscription.setUser(user);
            return subscriptionRepository.save(subscription);
        }
        return null;
    }


    public Subscription unsubscribeTopicByUser(Long userId, Long topicId){
        return subscriptionRepository.deleteByUser_IdAndTopic_Id(userId, topicId);
    }

    public void unsubscribeTopicByUser(User user, Topic topic){
        subscriptionRepository.deleteByUserAndTopic(user, topic);
    }

    public Invitation verifySubscriptionToken(String token){
        return invitationRepository.findByToken(token);
    }

    public Invitation invalidateToken(String token){
        Invitation invitation = invitationRepository.findByToken(token);
        invitation.setValid(false);
        return invitationRepository.save(invitation);
    }
}
