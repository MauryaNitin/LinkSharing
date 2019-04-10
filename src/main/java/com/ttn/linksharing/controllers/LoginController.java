package com.ttn.linksharing.controllers;

import com.ttn.linksharing.CO.LoginCO;
import com.ttn.linksharing.CO.SignupCO;
import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.services.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class LoginController {
    @Autowired
    LoginService loginService;

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginCO") LoginCO loginCO, BindingResult result, HttpServletRequest request, ModelMap model) {
        logger.debug(loginCO.toString());
        if (result.hasErrors()) {
            logger.warn(result.getFieldErrors().toString());
            return "homepage";
        }
        User user1 = loginService.loginUser(loginCO.getUsername(), loginCO.getPassword());
        model.addAttribute("signupCO", new SignupCO());
        HttpSession session = request.getSession(false);
        session.setAttribute("loggedInUserId", user1.getId());
        return "redirect:/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
}
