package com.ttn.linksharing.services;

import com.ttn.linksharing.CO.TopicCO;
import com.ttn.linksharing.entities.Subscription;
import com.ttn.linksharing.entities.Topic;
import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.enums.Seriousness;
import com.ttn.linksharing.repositories.TopicRepository;
import com.ttn.linksharing.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TopicService {
    @Autowired
    TopicRepository topicRepository;

    @Autowired
    UserService userService;

    @Autowired
    SubscriptionService subscriptionService;

    Logger logger = LoggerFactory.getLogger(TopicService.class);

    public Topic createTopic(TopicCO topicCO, Long userId){
        logger.info(topicCO.toString());
        Topic topic = new Topic(topicCO);
        User user = userService.getUserById(userId);
        topic.setUser(user);
        Subscription subscription = subscriptionService.createSubscription(user, topic);
        topic.getSubscriptions().add(subscription);
        topicRepository.save(topic);
        return topic;
    }

    public Topic getTopicById(Long topicId){
        Optional<Topic> optional = topicRepository.findById(topicId);
        Topic topic = optional.isPresent() ? optional.get() : null;
        if(topic == null){
//            throw new UserNotFoundException("No such User Exists!");
            logger.warn("Topic not Found!");
        }
        return topic;
    }
}
