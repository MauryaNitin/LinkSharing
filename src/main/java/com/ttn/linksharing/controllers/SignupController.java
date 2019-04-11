package com.ttn.linksharing.controllers;

import com.ttn.linksharing.CO.LoginCO;
import com.ttn.linksharing.CO.SignupCO;
import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.services.LoginService;
import com.ttn.linksharing.services.SignupService;
import com.ttn.linksharing.utils.CryptoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
    public String signup(@Valid @ModelAttribute("signupCO") SignupCO signupCO,
                         BindingResult result,
                         ModelMap model,
                         HttpServletRequest request) {
        if (!signupCO.getPassword().equals(signupCO.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "errors.signup.confirmPassword", "Password did not match!");
        }
        if (result.hasErrors()) {
            logger.warn(result.getFieldErrors().toString());
            model.addAttribute("loginCO", new LoginCO());
            return "homepage";
        }
        User user = new User(signupCO);
        User user1 = signupService.createUser(user);
        if (signupCO.getPhoto() != null) {
            uploader.saveProfilePicture(signupCO.getPhoto(), signupCO.getUsername());
        }
        if (user1 == null) {
            logger.error("Error occurred in saving user. Null returned from db.");
            return "errors/errors";
        }
        HttpSession session = request.getSession();
        User loggedInUser = loginService.loginUser(user.getUsername(), CryptoUtils.decrypt(user.getPassword()));
        session.setAttribute("loggedInUserId", loggedInUser.getId());
        return "redirect:/dashboard";
    }
}
