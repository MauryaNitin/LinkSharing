package com.ttn.linksharing.controllers;

import com.ttn.linksharing.CO.LoginCO;
import com.ttn.linksharing.CO.SignupCO;
import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.services.LoginService;
import com.ttn.linksharing.services.SignupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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

    @Autowired
    FileUploadController uploader;

    @Autowired
    LoginService loginService;

    Logger logger = LoggerFactory.getLogger(SignupController.class);

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("signupCO") SignupCO signupCO, BindingResult result, ModelMap model){
        logger.debug(signupCO.toString());
        if(!signupCO.getConfirmPassword().equals(signupCO.getPassword())){
            result.addError(new ObjectError("confirmPassword", "Password did not match"));
        }
        if(result.hasErrors()){
            logger.warn(result.getFieldErrors().toString());
//            model.addAttribute("loginCO", new LoginCO());
            return "homepage";
        }
        User user = new User(signupCO);
        User user1 = signupService.createUser(user);
        uploader.saveProfilePicture(signupCO.getPhoto(), signupCO.getUsername());
        if(user1 == null){
            logger.error("Error occurred in saving user. Null returned from db.");
            return "errors";
        }
//        else{
//            loginService.loginUser(user.getUsername(), user.getPassword());
//        }
        return "redirect:/dashboard";
    }
}
