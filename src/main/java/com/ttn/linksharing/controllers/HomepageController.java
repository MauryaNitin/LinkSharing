package com.ttn.linksharing.controllers;

import com.ttn.linksharing.CO.LoginCO;
import com.ttn.linksharing.CO.SignupCO;
import com.ttn.linksharing.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomepageController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homepage(Model model){
        model.addAttribute("loginCO", new LoginCO());
        model.addAttribute("signupCO", new SignupCO());
        return "homepage";
    }

//    @GetMapping("/")
//    public ModelAndView homepage(){
//        ModelAndView mv = new ModelAndView("homepage");
//        mv.addObject("loginCO", new LoginCO());
//        mv.addObject("signupCO", new SignupCO());
//        return mv;
//    }

}
