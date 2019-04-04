package com.ttn.linksharing.CO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginCO {

    @NotBlank(message = "Username cannot be empty!")
    @Size(min = 3, message = "Username must be more than 3 characters!")
    private String username;

    @NotBlank(message = "Password cannot be empty!")
    @Size(min = 5, message = "Password should be atleast 5 characters long!")
    private String password;

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
}
