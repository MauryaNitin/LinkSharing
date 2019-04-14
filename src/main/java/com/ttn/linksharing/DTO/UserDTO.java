package com.ttn.linksharing.DTO;

import com.ttn.linksharing.entities.Resource;
import com.ttn.linksharing.entities.Subscription;
import com.ttn.linksharing.entities.Topic;
import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.enums.Roles;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private List<Subscription> subscriptions = new ArrayList<>();
    private List<Topic> topics = new ArrayList<>();
    private String photo;
    private Roles role;
    private List<Resource> inboxResources = new ArrayList<>();

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.photo = user.getProfilePicturePath();
        this.role = user.getRole();
    }

    public UserDTO(){ }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public List<Resource> getInboxResources() {
        return inboxResources;
    }

    public void setInboxResources(List<Resource> inboxResources) {
        this.inboxResources = inboxResources;
    }
}
