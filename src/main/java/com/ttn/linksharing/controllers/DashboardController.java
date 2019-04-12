package com.ttn.linksharing.controllers;

import com.ttn.linksharing.CO.DocumentResourceCO;
import com.ttn.linksharing.CO.InvitationCO;
import com.ttn.linksharing.CO.LinkResourceCO;
import com.ttn.linksharing.CO.TopicCO;
import com.ttn.linksharing.DTO.UserDTO;
import com.ttn.linksharing.entities.Resource;
import com.ttn.linksharing.entities.Topic;
import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.services.ResourceService;
import com.ttn.linksharing.services.TopicService;
import com.ttn.linksharing.services.UserService;
import com.ttn.linksharing.utils.EmailServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class DashboardController {
    @Autowired
    UserService userService;

    @Autowired
    TopicService topicService;

    @Autowired
    ResourceService resourceService;

    @Autowired
    EmailServiceUtil emailService;

    Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @ModelAttribute
    public void getHeaderCOs(ModelMap model) {
        model.addAttribute("topicCO", new TopicCO());
        model.addAttribute("linkResourceCO", new LinkResourceCO());
        model.addAttribute("documentResourceCO", new DocumentResourceCO());
        model.addAttribute("invitationCO", new InvitationCO());
    }


    @GetMapping("/dashboard")
    public String showDashboardView(HttpSession session, ModelMap model) {
        if (session.getAttribute("loggedInUserId") == null) {
            logger.warn("Redirecting to Homepage, Request not Authorized.");
            return "redirect:/";
        }
        Long userId = (Long) session.getAttribute("loggedInUserId");

        UserDTO userDTO = userService.getUserDto(userId);

        List<Topic> trendingTopicsDTO = topicService.getTrendingTopics(5);

        model.addAttribute("userId", userId);
        model.addAttribute("userDTO", userDTO);
        model.addAttribute("trendingTopicsDTO", trendingTopicsDTO);

        return "dashboard";
    }

    @GetMapping("/search")
    public String getSearchResults(@RequestParam("query") String query, ModelMap model, HttpSession session) {
        if (session.getAttribute("loggedInUserId") == null) {
            return "redirect:/";
        }
        if(query.equals("") || query == null){
            return "redirect:/dashboard";
        }
        Long userId = (Long) session.getAttribute("loggedInUserId");
        query = query.trim().replaceAll(" +", " ");
        List<Topic> searchedTopics = topicService.searchTopicsByName(query, userId);
        List<Resource> searchedResources = resourceService.searchResoucesByDescription(query, userId);
        List<User> searchedUsers = userService.searchUsersByName(query, userId);
        model.addAttribute("query", query);
        model.addAttribute("searchedTopics", searchedTopics);
        model.addAttribute("searchedResources", searchedResources);
        model.addAttribute("searchedUsers", searchedUsers);

        UserDTO userDTO = userService.getUserDto(userId);

        List<Topic> trendingTopicsDTO = topicService.getTrendingTopics(5);

        List<Resource> topPosts = resourceService.getTopPosts(5);


        model.addAttribute("userId", userId);
        model.addAttribute("userDTO", userDTO);
        model.addAttribute("trendingTopicsDTO", trendingTopicsDTO);
        model.addAttribute("topPostsDTO", topPosts);

        return "search";
    }


}
