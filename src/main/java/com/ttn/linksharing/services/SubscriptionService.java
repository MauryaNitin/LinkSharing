package com.ttn.linksharing.services;

import com.ttn.linksharing.entities.Subscription;
import com.ttn.linksharing.entities.Topic;
import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    public Subscription createSubscription(User user, Topic topic){
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setTopic(topic);
        return subscription;
    }

    public List<Subscription> getSubscribedTopicsByUser(User user){
        return subscriptionRepository.findByUser(user);
    }
}
