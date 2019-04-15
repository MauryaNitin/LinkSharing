package com.ttn.linksharing.controllers;

import com.ttn.linksharing.CO.*;
import com.ttn.linksharing.DTO.UserDTO;
import com.ttn.linksharing.DTO.UserPublicDTO;
import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.enums.Roles;
import com.ttn.linksharing.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;
    Logger logger = LoggerFactory.getLogger(this.getClass());

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

        UserPublicDTO showUserDTO = userService.getPublicUserDto(id);
        model.addAttribute("showUser", showUserDTO);
        return "userProfile";
    }

    @GetMapping("/users")
    public String showUsers(ModelMap model,
                            HttpSession session,
                            @ModelAttribute("alertSuccess") String alertSuccess,
                            @ModelAttribute("alertFailed") String alertFailed,
                            @ModelAttribute("alertInfo") String alertInfo){
        if(session.getAttribute("loggedInUserId") == null){
            return "redirect:/";
        }
        Long userId = (Long)session.getAttribute("loggedInUserId");
        User user = userService.getUserById(userId);
        if(!user.getRole().equals(Roles.ADMIN)){
            return "redirect:/";
        }
        List<User> users = userService.getAllUsers(userId);
        model.addAttribute("usersList", users);

        UserDTO userDTO = userService.getUserDto(userId);
        model.addAttribute("userDTO", userDTO);

        model.addAttribute("topicCO", new TopicCO());
        model.addAttribute("linkResourceCO", new LinkResourceCO());
        model.addAttribute("documentResourceCO", new DocumentResourceCO());
        model.addAttribute("invitationCO", new InvitationCO());

        model.addAttribute("alertSuccess", alertSuccess);
        model.addAttribute("alertFailed", alertFailed);
        model.addAttribute("alertInfo", alertInfo);
        return "users";
    }

    @GetMapping("/users/editProfile")
    public String showEditProfile(ModelMap model, HttpSession session){
        if(session.getAttribute("loggedInUserId") == null){
            return "redirect:/";
        }
        Long userId = (Long)session.getAttribute("loggedInUserId");
        User user = userService.getUserById(userId);

        UserDTO userDTO = userService.getUserDto(userId);
        model.addAttribute("userDTO", userDTO);

        UpdateProfileCO updateProfileCO = new UpdateProfileCO(user);

        model.addAttribute("topicCO", new TopicCO());
        model.addAttribute("linkResourceCO", new LinkResourceCO());
        model.addAttribute("documentResourceCO", new DocumentResourceCO());
        model.addAttribute("invitationCO", new InvitationCO());
        model.addAttribute("editProfileCO", updateProfileCO);
        model.addAttribute("updatePasswordCO", new UpdatePasswordCO());
        return "editProfile";
    }

    @PostMapping("/users/{id}/edit")
    public String editProfile(@Valid @ModelAttribute UpdateProfileCO updateProfileCO,
                              BindingResult result,
                              @PathVariable("id") Long id,
                              HttpSession session){
        if(session.getAttribute("loggedInUserId") == null && session.getAttribute("loggedInUserId") != id){
            return "redirect:/";
        }
        if(result.hasErrors()){
            return "redirect:/users/editProfile";
        }
        userService.updateUserDetails(updateProfileCO, id);
        return "redirect:/dashboard";
    }

    @PostMapping("/users/{id}/updatePassword")
    public String updatePassword(@Valid @ModelAttribute UpdatePasswordCO updatePasswordCO,
                                 BindingResult result,
                                 @PathVariable("id") Long id,
                                 HttpSession session){
        if(session.getAttribute("loggedInUserId") == null && session.getAttribute("loggedInUserId") != id){
            return "redirect:/";
        }
        if(!updatePasswordCO.getPassword().equals(updatePasswordCO.getConfirmPassword())){
            result.rejectValue("confirmPassword", "error.confirmPassword", "Password did not match!");
            return "editProfile";
        }
        if(result.hasErrors()){
            return "editProfile";
        }
        userService.updatePassword(updatePasswordCO, id);
        return "redirect:/dashboard";
    }

    @PostMapping("/users/activate")
    public String activateUser(@RequestParam("userId") Long userId,
                               HttpSession session,
                               RedirectAttributes redirectAttributes){
        if(session.getAttribute("loggedInUserId") == null){
            return "redirect:/";
        }
        logger.warn("Activating User" + userId);

        if(userService.activateUser(userId) != null){
            redirectAttributes.addFlashAttribute("alertSuccess", "fragment/alerts :: userActivateSuccess");
            return "redirect:/users";
        }
        redirectAttributes.addFlashAttribute("alertFailed", "fragment/alerts :: userActivateFailed");
        return "redirect:/users";

    }

    @PostMapping("/users/deactivate")
    public String deactivateUser(@RequestParam("userId") Long userId,
                               HttpSession session,
                               RedirectAttributes redirectAttributes){
        if(session.getAttribute("loggedInUserId") == null){
            return "redirect:/";
        }
        logger.warn("Deactivating User" + userId);
        if(userService.deactivateUser(userId) != null){
            redirectAttributes.addFlashAttribute("alertSuccess", "fragment/alerts :: userDeactivateSuccess");
            return "redirect:/users";
        }
        redirectAttributes.addFlashAttribute("alertFailed", "fragment/alerts :: userDeactivateFailed");
        return "redirect:/users";
    }

}
