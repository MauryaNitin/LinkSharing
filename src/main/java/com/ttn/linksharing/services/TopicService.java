package com.ttn.linksharing.services;

import com.ttn.linksharing.DTO.TopicDTO;
import com.ttn.linksharing.entities.Topic;

public class TopicService {
    public Topic createTopic(TopicDTO topicDTO){
        Topic topic = new Topic(topicDTO);
        return topic;
    }

}
