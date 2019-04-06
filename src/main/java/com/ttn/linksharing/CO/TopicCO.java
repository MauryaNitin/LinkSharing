package com.ttn.linksharing.CO;

import com.ttn.linksharing.enums.Visibility;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TopicCO {
    @NotBlank(message = "Topic name cannot be empty!")
    @Size(min = 3, message = "Topic name must be more than 3 characters!")
    private String name;

    private Visibility visibility;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    @Override
    public String toString() {
        return "TopicCO{" +
                "name='" + name + '\'' +
                ", visibility=" + visibility +
                '}';
    }
}
