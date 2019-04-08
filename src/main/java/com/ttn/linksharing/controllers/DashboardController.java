package com.ttn.linksharing.controllers;

import com.ttn.linksharing.CO.DocumentResourceCO;
import com.ttn.linksharing.CO.InvitationCO;
import com.ttn.linksharing.CO.LinkResourceCO;
import com.ttn.linksharing.CO.TopicCO;
import com.ttn.linksharing.DTO.TrendingTopicsDTO;
import com.ttn.linksharing.DTO.UserDTO;
import com.ttn.linksharing.services.SubscriptionService;
import com.ttn.linksharing.services.TopicService;
import com.ttn.linksharing.services.UserService;
import com.ttn.linksharing.utils.EmailServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class DashboardController {
    @Autowired
    UserService userService;

    @Autowired
    SubscriptionService subscriptionService;

    @Autowired
    TopicService topicService;

    @Autowired
    EmailServiceUtil emailService;

    Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @GetMapping("/dashboard")
    public String showDashboardView(HttpSession session, ModelMap model){
        if(session.getAttribute("loggedInUserId") == null){
            logger.warn("Redirecting to Homepage, Request not Authorized.");
            return "redirect:/";
        }
        Long userId = (Long)session.getAttribute("loggedInUserId");

        UserDTO userDTO = userService.getUserDto(userId);
        TrendingTopicsDTO trendingTopicsDTO = new TrendingTopicsDTO();
        trendingTopicsDTO.setTrendingTopics(topicService.getTrendingTopics(5));

        model.addAttribute("userId", userId);
        model.addAttribute("userDTO", userDTO);
        model.addAttribute("trendingTopicsDTO", trendingTopicsDTO);

        model.addAttribute("topicCO", new TopicCO());
        model.addAttribute("linkResourceCO", new LinkResourceCO());
        model.addAttribute("documentResourceCO", new DocumentResourceCO());
        model.addAttribute("invitationCO", new InvitationCO());

        return "dashboard";
    }

    @PostMapping("/invite")
    public String sendInvitation(@Valid @ModelAttribute InvitationCO invitationCO, BindingResult result, HttpSession session){
        if(session.getAttribute("loggedInUserId") == null){
            logger.warn("Redirecting to Homepage, Request not Authorized.");
            return "redirect:/";
        }
        Long userId = (Long)session.getAttribute("loggedInUserId");
        if(result.hasErrors()){
            logger.warn("Email not valid for invitation");
            return "redirect:/dashboard";
        }

        subscriptionService.sendInvitation(userId, invitationCO);

        return "redirect:/dashboard";
    }
}
