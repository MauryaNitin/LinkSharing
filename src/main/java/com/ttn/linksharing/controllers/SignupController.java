package com.ttn.linksharing.controllers;

import com.ttn.linksharing.CO.LoginCO;
import com.ttn.linksharing.CO.SignupCO;
import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.services.LoginService;
import com.ttn.linksharing.services.SignupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Controller
@Validated
public class SignupController {
    @Autowired
    SignupService signupService;

    @Autowired
    LoginService loginService;

    Logger logger = LoggerFactory.getLogger(SignupController.class);

    @PostMapping("/signup")
    public String createUser(@Valid @ModelAttribute("signupCO") SignupCO signupCO, BindingResult result, Model model, LoginCO loginCO){
        logger.debug(signupCO.toString());
        if(result.hasErrors()){
            logger.warn(result.getFieldErrors().toString());
//            List<String> signupErrors = new ArrayList<>();
//            result.getFieldErrors().forEach(x -> signupErrors.add(x.getDefaultMessage()));
//            System.out.println("errors: " + signupErrors);
//            model.addAttribute("signupErrors", signupErrors);
            return "homepage";
        }
        User user = new User(signupCO);
        User user1 = signupService.createUser(user);
        if(user1 == null){
            logger.error("Error occurred in saving user. Null returned from db.");
            return "errors";
        }
//        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(user1.getUserId()).toUri();
//        return ResponseEntity.created(uri).build();

//        loginService.loginUser(user1.getUsername(), user1.getPassword());
        return "dashboard";
    }
}
