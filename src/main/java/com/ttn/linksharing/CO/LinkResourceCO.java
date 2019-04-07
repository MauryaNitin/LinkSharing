package com.ttn.linksharing.CO;

import com.ttn.linksharing.entities.Topic;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LinkResourceCO {
    @NotBlank(message = "Link Url cannot be empty!")
    @URL(message = "Enter a valid url!")
    private String url;

    @NotBlank(message = "Description cannot be blank!")
    @Size(min = 5, message = "Description should be between 5 to 100 characters")
    private String description;

    @NotNull(message = "Please select a topic!")
    private Long topicId;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }
}
