package com.ttn.linksharing.controllers;

import com.ttn.linksharing.CO.DocumentResourceCO;
import com.ttn.linksharing.CO.InvitationCO;
import com.ttn.linksharing.CO.LinkResourceCO;
import com.ttn.linksharing.CO.TopicCO;
import com.ttn.linksharing.DTO.UserDTO;
import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @GetMapping("/users/{id}/profile")
    public String showUserProfile(@PathVariable("id") Long id, ModelMap model, HttpSession session){
        if(session.getAttribute("loggedInUserId") == null){
            return "redirect:/";
        }
        Long userId = (Long)session.getAttribute("loggedInUserId");
        UserDTO userDTO = userService.getUserDto(userId);
        model.addAttribute("userDTO", userDTO);

        model.addAttribute("topicCO", new TopicCO());
        model.addAttribute("linkResourceCO", new LinkResourceCO());
        model.addAttribute("documentResourceCO", new DocumentResourceCO());
        model.addAttribute("invitationCO", new InvitationCO());

        UserDTO showUserDTO  = userService.getUserDto(id);
        model.addAttribute("showUser", showUserDTO);
        return "userProfile";
    }
}
