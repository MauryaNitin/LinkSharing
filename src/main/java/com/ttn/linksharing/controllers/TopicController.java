package com.ttn.linksharing.controllers;

import com.ttn.linksharing.CO.DocumentResourceCO;
import com.ttn.linksharing.CO.InvitationCO;
import com.ttn.linksharing.CO.LinkResourceCO;
import com.ttn.linksharing.CO.TopicCO;
import com.ttn.linksharing.DTO.TopicDTO;
import com.ttn.linksharing.DTO.UserDTO;
import com.ttn.linksharing.entities.Topic;
import com.ttn.linksharing.enums.Visibility;
import com.ttn.linksharing.services.TopicService;
import com.ttn.linksharing.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/topic")
public class TopicController {
    @Autowired
    TopicService topicService;

    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/create")
    public String createTopic(@Valid @ModelAttribute TopicCO topicCO, BindingResult result, HttpSession session){
        Long loggedInUserId;
        if(session.getAttribute("loggedInUserId") != null){
            loggedInUserId = (Long)session.getAttribute("loggedInUserId");
        }
        else{
            return "redirect:/dashboard";
        }
        topicService.createTopic(topicCO, loggedInUserId);
        return "redirect:/dashboard";
    }

    @GetMapping("/{id}")
    public String showTopic(@PathVariable("id") Long id, ModelMap model, HttpSession session){
        if(session.getAttribute("loggedInUserId") == null){
            return "redirect:/";
        }
        Long userId = (Long) session.getAttribute("loggedInUserId");
        Topic topic = topicService.getTopicById(id);
        if(!topic.getUser().getId().equals(userId) && topic.getVisibility() == Visibility.PRIVATE){
            logger.warn("User not authorized to view this private topic!");
            return "redirect:/dashboard";
        }
        TopicDTO topicDTO = new TopicDTO(topic);
        UserDTO userDTO = userService.getUserDto(userId);
        model.addAttribute("userDTO", userDTO);
        model.addAttribute("topicCO", new TopicCO());
        model.addAttribute("linkResourceCO", new LinkResourceCO());
        model.addAttribute("documentResourceCO", new DocumentResourceCO());
        model.addAttribute("invitationCO", new InvitationCO());
        model.addAttribute("topicDTO", topicDTO);
        return "topic";
    }
}
