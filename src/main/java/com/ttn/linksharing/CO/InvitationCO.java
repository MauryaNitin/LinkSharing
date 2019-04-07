package com.ttn.linksharing.CO;

import com.sun.istack.internal.NotNull;

import javax.validation.constraints.Email;

public class InvitationCO {
    @Email(message = "Provided email is not Valid!")
    private String email;

    private Long topicId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }
}
