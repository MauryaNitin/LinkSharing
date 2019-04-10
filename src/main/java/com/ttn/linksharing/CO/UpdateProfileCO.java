package com.ttn.linksharing.CO;

import com.ttn.linksharing.entities.User;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UpdateProfileCO {
    @NotBlank(message = "First name cannot be empty!")
    @Size(min=2, message = "First name should have more than 2 characters!")
    private String firstname;

    @NotBlank(message = "Last name cannot be empty!")
    @Size(min=2, message = "Last name should have more than 2 characters!")
    private String lastname;

    @NotBlank(message = "Username cannot be empty!")
    private String username;

    private MultipartFile photo;

    public UpdateProfileCO(User user) {
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.username = user.getUsername();
    }

    public UpdateProfileCO(){

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }
}
