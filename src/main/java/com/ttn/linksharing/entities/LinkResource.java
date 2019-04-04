package com.ttn.linksharing.entities;

import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class LinkResource extends Resource implements Serializable {
    @NotNull(message = "Url cannot be empty!")
    @Column(name = "link_url")
    @URL(message = "Url not valid!")
    private String linkUrl;

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
}
