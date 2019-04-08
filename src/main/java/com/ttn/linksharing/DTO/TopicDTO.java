package com.ttn.linksharing.DTO;

import com.ttn.linksharing.entities.Topic;

public class TopicDTO {
    private Topic topic;

    public TopicDTO(Topic topic) {
        this.topic = topic;
    }
    public TopicDTO(){}

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
