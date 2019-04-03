package com.ttn.linksharing.controllers;

import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class LoginController {
    @Autowired
    LoginService loginService;

    @ModelAttribute
    public ModelAndView checkSession(HttpSession session,HttpServletRequest request, HttpServletResponse response){
        User user = (User)session.getAttribute("loggedInUser");
        if(user != null){
            ModelAndView modelAndView = new ModelAndView("dashboard");
            return modelAndView;
        }
        else{
            ModelAndView modelAndView = new ModelAndView("homepage");
            return modelAndView;
        }

    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("user") User user, BindingResult result, HttpServletRequest request, Model model){
        if(result.hasErrors()){
            model.addAttribute("loginErrorsList", result.getFieldErrors());
            return "homepage";
        }
        User user1 = loginService.loginUser(user.getUsername(), user.getPassword());
        HttpSession session = request.getSession();
        session.setAttribute("loggedInUser", user1);
        return "dashboard";
    }
}
