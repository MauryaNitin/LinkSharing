package com.ttn.linksharing.controllers;

import com.ttn.linksharing.DTO.UserDTO;
import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
public class DashboardController {
    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @GetMapping("/dashboard")
    public String showDashboardView(HttpSession session, Model model){
        if(session.getAttribute("loggedInUserId") == null){
            logger.warn("Redirecting to Homepage, Request not Authorized.");
            return "redirect:/";
        }
        Long userId = (Long)session.getAttribute("loggedInUserId");
        model.addAttribute("userId", userId);
        UserDTO userDTO = userService.getUserDto(userId);
        logger.info(userDTO.toString());
        model.addAttribute("userDto", userDTO);
        return "dashboard";
    }

}
