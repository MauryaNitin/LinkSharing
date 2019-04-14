package com.ttn.linksharing.DTO;

import com.ttn.linksharing.entities.Rating;
import com.ttn.linksharing.entities.Resource;
import com.ttn.linksharing.entities.Topic;
import com.ttn.linksharing.entities.User;

public class ResourceDTO {
    private Long id;
    private User user;
    private Topic topic;
    private String description;
    private Resource resource;
    private String type;
    private Rating rating;

    public ResourceDTO(Resource resource) {
        this.id = resource.getId();
        this.description = resource.getDescription();
        if (resource.getClass().getSimpleName().equals("LinkResource")) {
            this.type = "linkResource";
        } else if (resource.getClass().getSimpleName().equals("DocumentResource")) {
            this.type = "documentResource";
        }
        this.resource = resource;
        this.user = resource.getUser();
        this.topic = resource.getTopic();
    }

    public ResourceDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
}
