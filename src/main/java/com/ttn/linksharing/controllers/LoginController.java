package com.ttn.linksharing.controllers;

import com.ttn.linksharing.CO.LoginCO;
import com.ttn.linksharing.CO.SignupCO;
import com.ttn.linksharing.CO.UpdatePasswordCO;
import com.ttn.linksharing.entities.ForgotPassword;
import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.services.LoginService;
import com.ttn.linksharing.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class LoginController {
    @Autowired
    LoginService loginService;

    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginCO") LoginCO loginCO, BindingResult result, HttpServletRequest request, ModelMap model, SignupCO signupCO) {
        logger.debug(loginCO.toString());
        if (result.hasErrors()) {
            logger.warn(result.getFieldErrors().toString());
            return "homepage";
        }
        User user1 = loginService.loginUser(loginCO.getUsername(), loginCO.getPassword());
        model.addAttribute("signupCO", new SignupCO());
        HttpSession session = request.getSession(false);
        session.setAttribute("loggedInUserId", user1.getId());
        return "redirect:/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/forgotPassword")
    public String forgotPasswordController(@RequestParam("username") String username){
        if(loginService.checkForAlreadyExistingToken(username)){
            return "fragments/alerts :: emailAreadySent";
        }
        else{
            if(loginService.forgotPassword(username)){
                return "fragments/alerts :: forgetPassword-acknowledgement";
            }
            return "fragments/alerts :: noUserExist";
        }
    }

    @GetMapping("/reset")
    public String showResetPasswordPage(@RequestParam("token") String token, HttpServletRequest request, ModelMap model){
        ForgotPassword forgotPassword = loginService.verifyResetToken(token);
        if(forgotPassword != null){
            HttpSession session = request.getSession();
            session.setAttribute("userId", forgotPassword.getUserId());
            session.setAttribute("token", token);
            model.addAttribute("updatePasswordCO", new UpdatePasswordCO());
            return "resetPassword";
        }
        else{
            return "errors/linkExpired";
        }
    }

    @PostMapping("/reset")
    public String resetPassword(@Valid @ModelAttribute UpdatePasswordCO updatePasswordCO,
                                BindingResult result,
                                HttpSession session,
                                ModelMap model){
        if(session.getAttribute("userId") == null){
            logger.warn("Not authenticated to reset password");
        }
        else{
            if(!updatePasswordCO.getPassword().equals(updatePasswordCO.getConfirmPassword())){
                result.rejectValue("confirmPassword", "error.confirmPassword", "Password did not match!");
            }
            if(result.hasErrors()){
                return "resetPassword";
            }
            else{
                Long userId = (Long)session.getAttribute("userId");
                userService.updatePassword(updatePasswordCO, userId);
                loginService.deleteForgotPasswordToken(userId);
            }
        }
        session.invalidate();
        return "redirect:/";
    }
}
