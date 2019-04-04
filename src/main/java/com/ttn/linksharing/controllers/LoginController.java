package com.ttn.linksharing.controllers;

import com.ttn.linksharing.CO.LoginCO;
import com.ttn.linksharing.CO.SignupCO;
import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.services.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

@Controller
public class LoginController {
    @Autowired
    LoginService loginService;

    Logger logger = LoggerFactory.getLogger(LoginController.class);

//    @ModelAttribute
//    public ModelAndView checkSession(HttpSession session,HttpServletRequest request, HttpServletResponse response){
//        User user = (User)session.getAttribute("loggedInUser");
//        if(user != null){
//            ModelAndView modelAndView = new ModelAndView("dashboard");
//            return modelAndView;
//        }
//        else{
//            ModelAndView modelAndView = new ModelAndView("homepage");
//            return modelAndView;
//        }
//
//    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginCO") LoginCO loginCO, BindingResult result, HttpServletRequest request, Model model, SignupCO signupCO){
        logger.debug(loginCO.toString());
        if(result.hasErrors()){
            logger.warn(result.getFieldErrors().toString());
//            List<String> errors = new ArrayList<>();
//            result.getFieldErrors().forEach(x -> errors.add(x.getDefaultMessage()));
//            model.addAttribute("loginErrors", errors);
            return "homepage";
        }
        User user1 = loginService.loginUser(loginCO.getUsername(), loginCO.getPassword());
        HttpSession session = request.getSession();
        session.setAttribute("loggedInUser", user1);
        return "redirect:/dashboard";
    }
}
