package com.ttn.linksharing.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ttn.linksharing.enums.Seriousness;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Subscription implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonManagedReference
    @ManyToOne
    private User user;

    @JsonManagedReference
    @ManyToOne
    private Topic topic;

    @Enumerated(EnumType.STRING)
    private Seriousness seriousness = Seriousness.CASUAL;

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

    public Seriousness getSeriousness() {
        return seriousness;
    }

    public void setSeriousness(Seriousness seriousness) {
        this.seriousness = seriousness;
    }
}
