package com.ttn.linksharing.controllers;

import com.ttn.linksharing.CO.*;
import com.ttn.linksharing.DTO.TopicDTO;
import com.ttn.linksharing.DTO.UserDTO;
import com.ttn.linksharing.entities.Invitation;
import com.ttn.linksharing.entities.Subscription;
import com.ttn.linksharing.entities.Topic;
import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.enums.Visibility;
import com.ttn.linksharing.services.SubscriptionService;
import com.ttn.linksharing.services.TopicService;
import com.ttn.linksharing.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class TopicController {
    @Autowired
    TopicService topicService;

    @Autowired
    SubscriptionService subscriptionService;

    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/topic/create")
    public String createTopic(@Valid @ModelAttribute TopicCO topicCO, BindingResult result, HttpSession session) {
        Long loggedInUserId;
        if (session.getAttribute("loggedInUserId") != null) {
            loggedInUserId = (Long) session.getAttribute("loggedInUserId");
        } else {
            return "redirect:/dashboard";
        }
        Topic topic = topicService.createTopic(topicCO, loggedInUserId);
        if (topic != null) {
            return "fragments/alerts :: createTopicSuccess";
        }
        return "fragments/alerts :: createTopicFailed";
    }

    @GetMapping("/topic/{id}")
    public String showTopic(@PathVariable("id") Long id, ModelMap model, HttpSession session) {
        if (session.getAttribute("loggedInUserId") == null) {
            return "redirect:/";
        }
        Long userId = (Long) session.getAttribute("loggedInUserId");
        Topic topic = topicService.getTopicById(id);
        if (!topic.getUser().getId().equals(userId) && topic.getVisibility() == Visibility.PRIVATE) {
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

    @PostMapping("/invite")
    public String sendInvitation(@Valid @ModelAttribute InvitationCO invitationCO,
                                 BindingResult result,
                                 HttpSession session) {
        if (session.getAttribute("loggedInUserId") == null) {
            logger.warn("Redirecting to Homepage, Request not Authorized.");
            return "redirect:/";
        }
        Long userId = (Long) session.getAttribute("loggedInUserId");
        if (result.hasErrors()) {
            logger.warn("Email not valid for invitation");
            return "fragments/alerts :: emailNotValid";
        }

        if (subscriptionService.sendInvitation(userId, invitationCO)) {
            return "fragments/alerts :: invitationSuccess";
        } else {
            return "fragments/alerts :: invitationFailed";
        }
    }

    @GetMapping("/subscribe")
    public String subscribePrivateTopicByEmail(@RequestParam("token") String token, ModelMap model, HttpSession session) {
        Invitation invitation = subscriptionService.verifySubscriptionToken(token);
        if (invitation != null) {
            Long loginId = (Long) session.getAttribute("loggedInUserId");
            if (loginId != null) {
                if (invitation.getReceiverId() != loginId) {
                    return "fragments/alerts :: subscriptionRequestNotAuthorized";
                }
                subscriptionService.subscribeTopic(invitation.getReceiverId(), invitation.getTopicId());
                subscriptionService.invalidateToken(token);
                return "fragments/alerts :: subscriptionSuccess";
            } else {
                session.setAttribute("subscriptionToken", token);
                model.addAttribute("loginCO", new LoginCO());
                return "login";
            }
        }
        return "errors/linkExpired";
    }

    @GetMapping("/topic/{topicId}/subscribe")
    public String subscribePublicTopic(@PathVariable("topicId") Long topicId,
                                       ModelMap model,
                                       HttpSession session,
                                       RedirectAttributes redirectAttributes){
        if (session.getAttribute("loggedInUserId") == null) {
            logger.warn("Redirecting to Homepage, Request not Authorized.");
            return "redirect:/";
        }
        Long userId = (Long) session.getAttribute("loggedInUserId");
        Subscription subscription = subscriptionService.subscribePublicTopic(userId, topicId);
        if (subscription != null) {
            redirectAttributes.addFlashAttribute("alertSuccess", "fragments/alerts :: subscriptionSuccess");
            return "redirect:/dashboard";
        }
        redirectAttributes.addFlashAttribute("alertFailed", "fragments/alerts :: privateSubscriptionFailed");
        return "redirect:/dashboard";
    }

    @GetMapping("/topic/{topicId}/unsubscribe")
    public String unsubscribeTopic(@PathVariable("topicId") Long topicId,
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes) {
        if (session.getAttribute("loggedInUserId") == null) {
            logger.warn("Redirecting to Homepage, Request not Authorized.");
            return "redirect:/";
        }
        Long userId = (Long) session.getAttribute("loggedInUserId");
        User user = userService.getUserById(userId);
        Topic topic  = topicService.getTopicById(topicId);
        if (subscriptionService.unsubscribeTopicByUser(user, topic) != null) {
            redirectAttributes.addFlashAttribute("alertSuccess", "fragments/alerts :: unsubscribeSuccess");
            return "redirect:/dashboard";
        }
        redirectAttributes.addFlashAttribute("alertFailed", "fragments/alerts :: unsubscribeFailed");
        return "redirect:/dashboard";
    }

    @PutMapping("/topic/{topicId}/edit")
    public String editTopic(@PathVariable("topicId") Long topicId,
                            @ModelAttribute("topicCO") TopicCO topicCO,
                            HttpSession session,
                            RedirectAttributes redirectAttributes){
        if (session.getAttribute("loggedInUserId") == null) {
            logger.warn("Redirecting to Homepage, Request not Authorized.");
            return "redirect:/";
        }
        Long userId = (Long) session.getAttribute("loggedInUserId");
        if(topicService.updateTopic(topicId, topicCO, userId) != null){
            redirectAttributes.addFlashAttribute("alertSuccess", "fragments/alerts :: editTopicSuccess");
            return "redirect:/dashboard";
        }
        redirectAttributes.addFlashAttribute("alertFailed", "fragments/alerts :: editTopicFailed");
        return "redirect:/dashboard";
    }


    @DeleteMapping("/topic/{topicId}/delete")
    public String deleteTopic(@PathVariable("topicId") Long topicId,
                              HttpSession session,
                              RedirectAttributes redirectAttributes){
        if (session.getAttribute("loggedInUserId") == null) {
            logger.warn("Redirecting to Homepage, Request not Authorized.");
            return "redirect:/";
        }
        Long userId = (Long) session.getAttribute("loggedInUserId");
        if(topicService.deleteTopic(topicId, userId) != null){
            redirectAttributes.addFlashAttribute("alertSuccess", "fragments/alerts :: deleteTopicSuccess");
            return "redirect:/dashboard";
        }
        redirectAttributes.addFlashAttribute("alertFailed", "fragments/alerts :: deleteTopicFailed");
        return "redirect:/dashboard";
    }


}
