package com.ttn.linksharing.controllers;

import com.ttn.linksharing.CO.LoginCO;
import com.ttn.linksharing.CO.SignupCO;
import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.services.SignupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class SignupController {
    @Autowired
    SignupService signupService;

    Logger logger = LoggerFactory.getLogger(SignupController.class);

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("signupCO") SignupCO signupCO, BindingResult result, HttpServletRequest request, Model model, @RequestParam("photo") MultipartFile photo){
        logger.debug(signupCO.toString());
        if(result.hasErrors()){
            logger.warn(result.getFieldErrors().toString());
            model.addAttribute("loginCO", new LoginCO());
            return "homepage";
        }
        User user = new User(signupCO);
        try {
            byte[] profilePic = signupCO.getPhoto().getBytes();
            Path path = Paths.get(System.getProperty("user.dir") + "/" + signupCO.getPhoto().getOriginalFilename());
            Files.write(path, profilePic);
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user1 = signupService.createUser(user);
        if(user1 == null){
            logger.error("Error occurred in saving user. Null returned from db.");
            return "errors";
        }
//        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(user1.getUserId()).toUri();
//        return ResponseEntity.created(uri).build();
//        user1 = loginService.loginUser(user.getUsername(), user.getPassword());
//        HttpSession session = request.getSession();
//        session.setAttribute("loggedInUser", user1);
        return "redirect:/dashboard";
    }
}
