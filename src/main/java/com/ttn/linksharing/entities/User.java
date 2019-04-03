package com.ttn.linksharing.entities;

import com.ttn.linksharing.enums.Roles;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @NotNull(message = "First Name cannot be empty!")
    @Column(name = "first_name")
    @Size(min = 2, message = "First name should have more than 2 Characters")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @NotNull(message = "Username cannot be empty!")
    private String username;

    @Column(unique = true)
    @NotNull(message = "Email cannot be empty!")
    @Email(message = "Please provide a valid email address!")
//    @Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address!")
    private String email;


    @NotNull(message = "Password cannot be empty!")
    private String password;

    @Column(name = "profile_pic")
    private String profilePicturePath;


    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_on")
    @CreationTimestamp
    private Date createdOn;

    @Column(name = "updated_on")
    @UpdateTimestamp
    private Date updatedOn;

    private Roles role;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePicturePath() {
        return profilePicturePath;
    }

    public void setProfilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

}
