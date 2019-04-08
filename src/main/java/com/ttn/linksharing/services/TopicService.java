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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Topic topic = optional.orElse(null);
        if(topic == null){
//            throw new UserNotFoundException("No such User Exists!");
            logger.warn("Topic not Found!");
        }
        return topic;
    }

    public List<Topic> getTopicsByUserId(Long userId){
        return topicRepository.findByUserId(userId);
    }

    public List<Topic> getTrendingTopics(Integer count){
        return topicRepository.getTrendingTopics(new PageRequest(0,count)).getContent();
    }

}
