package com.ttn.linksharing.DTO;

import com.ttn.linksharing.entities.*;

public class ResourceDTO {
    private User user;
    private Topic topic;
    private String description;
    private Resource resource;
    private String type;

    public ResourceDTO(Resource resource){
        this.description = resource.getDescription();
        if(resource.getClass().getSimpleName().equals("LinkResource")){
            this.type = "linkResource";
        }
        else if(resource.getClass().getSimpleName().equals("DocumentResource")) {
            this.type = "documentResource";
        }
        this.resource = resource;
        this.user = resource.getUser();
        this.topic = resource.getTopic();
    }

    public ResourceDTO(){

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
}
