package com.ttn.linksharing.DTO;

import com.ttn.linksharing.entities.Subscription;
import com.ttn.linksharing.entities.Topic;

public class TopicDTO {
    private Topic topic;
    private Subscription subscription;

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

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }
}
