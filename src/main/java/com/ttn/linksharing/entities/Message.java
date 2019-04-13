package com.ttn.linksharing.entities;

import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Where(clause = "read_status = true")
public class Message implements Serializable {

    @Id
    private Long id;

    private Resource resource;

    private User user;

    @Column(columnDefinition = "default false")
    private Boolean readStatus;

    public Long getId() {
        return id;
    }

    public Message setId(Long id) {
        this.id = id;
        return this;
    }

    public Resource getResource() {
        return resource;
    }

    public Message setResource(Resource resource) {
        this.resource = resource;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Message setUser(User user) {
        this.user = user;
        return this;
    }

    public Boolean getReadStatus() {
        return readStatus;
    }

    public Message setReadStatus(Boolean readStatus) {
        this.readStatus = readStatus;
        return this;
    }
}
