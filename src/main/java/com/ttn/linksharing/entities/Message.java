package com.ttn.linksharing.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Where(clause = "read_status = false")
public class Message implements Serializable {

    @Id
    private Long id;

    @JsonManagedReference
    @ManyToOne
    Resource resource;

    @JsonManagedReference
    @ManyToOne
    User user;

    @Column(columnDefinition = "default false")
    Boolean readStatus;

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
