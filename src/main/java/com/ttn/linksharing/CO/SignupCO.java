package com.ttn.linksharing.CO;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SignupCO {
    @NotBlank(message = "First name cannot be empty!")
    @Size(min=2, message = "First name should have more than 2 characters!")
    private String firstname;

    private String lastname;

    @Email(message = "Email not valid!")
    @NotBlank(message = "Email cannot be empty!")
    private String email;

    @NotBlank(message = "Username cannot be empty!")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 5, message = "Password should have more than 5 characters!")
    private String password;

    @NotBlank(message = "Confirm Password cannot be empty")
    private String confirmPassword;

    private MultipartFile photo;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "SignupCO{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
