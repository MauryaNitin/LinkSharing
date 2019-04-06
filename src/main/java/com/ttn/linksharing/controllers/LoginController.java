package com.ttn.linksharing.controllers;

import com.ttn.linksharing.CO.LoginCO;
import com.ttn.linksharing.CO.SignupCO;
import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.services.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String login(@Valid @ModelAttribute("loginCO") LoginCO loginCO, BindingResult result, HttpServletRequest request,HttpSession session, Model model) {
        if(request.getSession(false) == null){
            return "redirect:/dashboard";
        }
        else{
            logger.debug(loginCO.toString());
            if (result.hasErrors()) {
                logger.warn(result.getFieldErrors().toString());
                model.addAttribute("signupCO", new SignupCO());
                return "homepage";
            }
            User user1 = loginService.loginUser(loginCO.getUsername(), loginCO.getPassword());
            session = request.getSession(false);
            session.setAttribute("loggedInUserId", user1.getId());
            return "redirect:/dashboard";
        }
    }
}
