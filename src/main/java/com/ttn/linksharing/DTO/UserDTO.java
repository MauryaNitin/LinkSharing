package com.ttn.linksharing.DTO;

import com.ttn.linksharing.entities.Topic;
import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.services.UserService;

import java.util.List;

public class UserDTO {
    private String username;
    private String firstname;
    private String lastname;
    private List<Topic> subscriptions;
    private List<Topic> topics;
    private String photo;

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.photo = user.getProfilePicturePath();
    }

    public UserDTO(){ }

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

    public List<Topic> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Topic> subscriptions) {
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

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", subscriptions=" + subscriptions +
                ", topics=" + topics +
                ", photo='" + photo + '\'' +
                '}';
    }
}
