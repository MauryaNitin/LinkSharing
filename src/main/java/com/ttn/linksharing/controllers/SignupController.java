package com.ttn.linksharing.controllers;

import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.services.LoginService;
import com.ttn.linksharing.services.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Controller
public class SignupController {
    @Autowired
    SignupService signupService;

    @PostMapping("/signup")
    public ModelAndView createUser(@Valid @ModelAttribute("user") User user, BindingResult result){
        ModelAndView modelAndView = new ModelAndView();
        if(result.hasErrors()){
            modelAndView.addObject("signupErrorsList", result.getAllErrors());
            modelAndView.setViewName("homepage");
            return modelAndView;
        }
        User user1 = signupService.createUser(user);
        if(user1 == null){
            modelAndView.setViewName("errors");
            return modelAndView;
        }
//        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(user1.getUserId()).toUri();
//        return ResponseEntity.created(uri).build();
        modelAndView.setViewName("dashboard");
        return modelAndView;
    }
}
