package com.ttn.linksharing.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Resource implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Description cannot cannot be empty!")
    @Size(min = 2, message = "Description should be more than 2 characters!")
    private String description;

    @ManyToOne
    private User user;

    @ManyToOne
    private Topic topic;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "resource")
    private List<Rating> rating = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
