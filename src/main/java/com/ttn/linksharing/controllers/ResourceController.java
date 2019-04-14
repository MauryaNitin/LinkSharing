package com.ttn.linksharing.controllers;

import com.ttn.linksharing.CO.DocumentResourceCO;
import com.ttn.linksharing.CO.InvitationCO;
import com.ttn.linksharing.CO.LinkResourceCO;
import com.ttn.linksharing.CO.TopicCO;
import com.ttn.linksharing.DTO.ResourceDTO;
import com.ttn.linksharing.DTO.UserDTO;
import com.ttn.linksharing.entities.DocumentResource;
import com.ttn.linksharing.entities.LinkResource;
import com.ttn.linksharing.entities.Resource;
import com.ttn.linksharing.entities.Topic;
import com.ttn.linksharing.enums.Visibility;
import com.ttn.linksharing.services.ResourceService;
import com.ttn.linksharing.services.TopicService;
import com.ttn.linksharing.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class ResourceController {
    @Autowired
    ResourceService resourceService;

    @Autowired
    TopicService topicService;

    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @ModelAttribute
    public void getHeaderCOs(ModelMap model) {
        model.addAttribute("topicCO", new TopicCO());
        model.addAttribute("linkResourceCO", new LinkResourceCO());
        model.addAttribute("documentResourceCO", new DocumentResourceCO());
        model.addAttribute("invitationCO", new InvitationCO());
    }

    @PostMapping("/resources/link/create")
    public String createLinkResource(@Valid @ModelAttribute LinkResourceCO linkResourceCO,
                                     BindingResult result,
                                     HttpSession session) {

        if (session.getAttribute("loggedInUserId") == null) {
            logger.warn("Redirecting to Homepage, Request not Authorized.");
            return "redirect:/dashboard";
        }

        Long userId = (Long) session.getAttribute("loggedInUserId");
        if (result.hasErrors()) {
            logger.warn(result.getFieldErrors().toString());
            return "redirect:/dashboard";
        }

        LinkResource resource = resourceService.createLinkResource(userId, linkResourceCO);
        if (resource == null) {
            logger.error("Error occured in creating resource");
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/resources/document/create")
    public String createDocumentResource(@Valid @ModelAttribute DocumentResourceCO documentResourceCO,
                                         BindingResult result,
                                         HttpSession session) {

        if (session.getAttribute("loggedInUserId") == null) {
            logger.warn("Redirecting to Homepage, Request not Authorized.");
            return "redirect:/dashboard";
        }

        Long userId = (Long) session.getAttribute("loggedInUserId");
        if (result.hasErrors()) {
            logger.warn(result.getFieldErrors().toString());
            return "redirect:/dashboard";
        }


        DocumentResource resource = resourceService.createDocumentResource(userId, documentResourceCO);
        if (resource == null) {
            logger.error("Error occured in creating Document resource");
        }

        return "redirect:/dashboard";
    }

    @GetMapping("/topic/{topicId}/post/{postId}")
    public String viewPost(@PathVariable("topicId") Long topicId,
                           @PathVariable("postId") Long postId,
                           HttpSession session,
                           Model model) {

        if (session.getAttribute("loggedInUserId") == null) {
            return "redirect:/";
        }
        Long userId = (Long) session.getAttribute("loggedInUserId");
        Topic topic = topicService.getTopicById(topicId);
        if (!topic.getUser().getId().equals(userId) && topic.getVisibility() == Visibility.PRIVATE) {
            logger.warn("User not authorized to view this private topic!");
            return "redirect:/dashboard";
        }
        Resource resource = resourceService.getResourceById(postId);
        ResourceDTO resourceDTO = new ResourceDTO(resource);
        resourceDTO.setRating(resourceService.getRatingOfResourceOfUser(userId, postId));
        model.addAttribute("resourceDTO", resourceDTO);

        UserDTO userDTO = userService.getUserDto(userId);
        model.addAttribute("userDTO", userDTO);

        List<Topic> trendingTopicsDTO = topicService.getTrendingTopics(5);
        model.addAttribute("trendingTopicsDTO", trendingTopicsDTO);

        return "posts";
    }

    @PostMapping("/resource/markAsRead")
    public String markResourceAsRead(@RequestBody Long resourceId,
                                     ModelMap model,
                                     HttpSession session) {
        if (session.getAttribute("loggedInUserId") == null) {
            return "redirect:/";
        }
        Long userId = (Long) session.getAttribute("loggedInUserId");
        resourceService.markResourceAsRead(resourceId, userId);
        return "dashboard";
    }

    @PostMapping("/resource/{resourceId}/rate")
    public String rateResource(@PathVariable("resourceId") Long resourceId,
                               @RequestParam("rate") Integer rate,
                               ModelMap model,
                               HttpSession session){
        if (session.getAttribute("loggedInUserId") == null) {
            return "redirect:/";
        }
        Long userId = (Long) session.getAttribute("loggedInUserId");
        resourceService.rateResource(userId, resourceId, rate);
        return "dashboard";
    }

    @PostMapping("/resource/{resourceId}/edit")
    public String updateResource(@PathVariable("resourceId") Long resourceId,
                                 @RequestParam("description") String description,
                                 ModelMap model,
                                 HttpSession session){
        if (session.getAttribute("loggedInUserId") == null) {
            return "redirect:/";
        }
        resourceService.updateResourceDescription(resourceId, description);
        return "dashboard";
    }

    @DeleteMapping("/resource/{resourceId}/delete")
    public String deleteResource(@PathVariable("resourceId") Long resourceId,
                                 HttpSession session){
        if (session.getAttribute("loggedInUserId") == null) {
            return "redirect:/";
        }
        resourceService.deleteResource(resourceId);
        return "dashboard";
    }
}
