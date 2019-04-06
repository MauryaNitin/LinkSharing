package com.ttn.linksharing.controllers;

import com.ttn.linksharing.CO.TopicCO;
import com.ttn.linksharing.entities.Topic;
import com.ttn.linksharing.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/topic")
public class TopicController {
    @Autowired
    TopicService topicService;

//    @ModelAttribute
//    public String checkSession(HttpSession session){
//        if(session.getAttribute("loggedInUserId") != null){
//            loggedInUserId = (Long)session.getAttribute("loggedInUserId");
//        }
//        return "redirect:/dashboard";
//    }

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
}
