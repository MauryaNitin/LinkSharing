package com.ttn.linksharing.services;

import com.ttn.linksharing.CO.TopicCO;
import com.ttn.linksharing.entities.Subscription;
import com.ttn.linksharing.entities.Topic;
import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.enums.Roles;
import com.ttn.linksharing.enums.Seriousness;
import com.ttn.linksharing.enums.Visibility;
import com.ttn.linksharing.repositories.TopicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Topic createTopic(TopicCO topicCO, Long userId) {
        if(topicCO.getVisibility() == Visibility.PUBLIC){
            if(topicRepository.findFirstByName(topicCO.getName()) != null){
                return null;
            }
        }
        else{
            if(topicRepository.findFirstByNameAndUser_Id(topicCO.getName(), userId) != null){
                return null;
            }
        }
        Topic topic = new Topic(topicCO);
        User user = userService.getUserById(userId);
        topic.setUser(user);
        Subscription subscription = subscriptionService.createSubscription(user, topic);
        topic.getSubscriptions().add(subscription);
        topicRepository.save(topic);
        return topic;
    }

    public Topic getTopicById(Long topicId) {
        Optional<Topic> optional = topicRepository.findById(topicId);
        Topic topic = optional.orElse(null);
        if (topic == null) {
//            throw new UserNotFoundException("No such User Exists!");
            logger.warn("Topic not Found!");
        }
        return topic;
    }

    public List<Topic> getTopicsByUserId(Long userId) {
        return topicRepository.findByUserId(userId);
    }

    public List<Topic> getTrendingTopics(Integer count) {
        return topicRepository.getTrendingTopics(new PageRequest(0, count)).getContent();
    }

    public List<Topic> searchTopicsByName(String query, Long userId) {
        return topicRepository.findByNameLike("%" + query + "%")
                .stream()
                .filter(x -> (x.getVisibility() == Visibility.PUBLIC || (x.getUser().getId() == userId)))
                .collect(Collectors.toList());
    }

    public Topic updateTopic(Long topicId, TopicCO topicCO, Long userId){
        Topic topic  = getTopicById(topicId);
        if(!topic.getUser().getId().equals(userId) && userService.getUserById(userId).getRole() != Roles.ADMIN){
            return null;
        }
        topic.setName(topicCO.getName());
        topic.setVisibility(topicCO.getVisibility());
        return topicRepository.save(topic);
    }

    @Transactional
    public Integer deleteTopic(Long topicId, Long userId) {
        Topic topic = getTopicById(topicId);
        User user = userService.getUserById(userId);
        if (!topic.getUser().getId().equals(user.getId()) && user.getRole() != Roles.ADMIN) {
            return null;
        }
        return topicRepository.deleteTopicByIdAndUser_Id(topic.getId(), user.getId());
    }
}
