package com.ttn.linksharing.DTO;

import com.ttn.linksharing.entities.Resource;
import com.ttn.linksharing.entities.Subscription;
import com.ttn.linksharing.entities.Topic;
import com.ttn.linksharing.entities.User;

import java.util.List;

public class UserPublicDTO {
    private User user;
    private List<Topic> topics;
    private List<Subscription> subscriptions;
    private List<Resource> resources;

    public UserPublicDTO(User user) {
        this.user = user;
    }

    public UserPublicDTO(){}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }
}
