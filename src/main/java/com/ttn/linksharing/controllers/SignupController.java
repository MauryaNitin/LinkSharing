package com.ttn.linksharing.controllers;

import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.services.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class SignupController {
    @Autowired
    SignupService signupService;

    @PostMapping("/signup")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User user1 = signupService.createUser(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(user1.getUserId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
