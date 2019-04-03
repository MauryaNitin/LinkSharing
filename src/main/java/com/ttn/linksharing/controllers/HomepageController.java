package com.ttn.linksharing.controllers;

import com.ttn.linksharing.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomepageController {

    @RequestMapping("/")
    public ModelAndView homepage(){
        ModelAndView mv = new ModelAndView("homepage");
        mv.addObject("user", new User());
        return mv;
    }
}
