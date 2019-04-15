package com.ttn.linksharing.controllers;

import com.ttn.linksharing.CO.LoginCO;
import com.ttn.linksharing.CO.SignupCO;
import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.services.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class HomepageController {

    @Autowired
    ResourceService resourceService;

    @GetMapping("/")
    public String homepage(ModelMap model, HttpSession session){
        model.addAttribute("loginCO", new LoginCO());
        model.addAttribute("signupCO", new SignupCO());
        model.addAttribute("topPosts", resourceService.getTopPosts(5));
        if(session.getAttribute("loggedInUserId") != null){
            return "redirect:/dashboard";
        }
        return "homepage";
    }

}
