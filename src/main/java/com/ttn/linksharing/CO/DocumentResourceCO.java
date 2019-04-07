package com.ttn.linksharing.CO;

import com.ttn.linksharing.entities.Topic;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DocumentResourceCO {
    @NotNull(message = "Please select a document!")
    private MultipartFile document;

    @NotBlank(message = "Description cannot be blank!")
    @Size(min = 5, message = "Description should be between 5 to 100 characters")
    private String description;

    @NotNull(message = "Please select a topic!")
    private Long topicId;

    public MultipartFile getDocument() {
        return document;
    }

    public void setDocument(MultipartFile document) {
        this.document = document;
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
