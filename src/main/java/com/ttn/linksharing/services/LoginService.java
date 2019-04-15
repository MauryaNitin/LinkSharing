package com.ttn.linksharing.services;

import com.ttn.linksharing.entities.ForgotPassword;
import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.exceptions.IncorrectPasswordException;
import com.ttn.linksharing.exceptions.UserNotFoundException;
import com.ttn.linksharing.repositories.ForgotPasswordRepository;
import com.ttn.linksharing.utils.CryptoUtils;
import com.ttn.linksharing.utils.EmailServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.time.*;
import static java.time.temporal.ChronoUnit.MINUTES;
import java.util.UUID;

@Service
public class LoginService {

    @Autowired
    UserService userService;

    @Autowired
    EmailServiceUtil emailService;

    @Autowired
    ForgotPasswordRepository forgotPasswordRepository;

    private Logger logger = LoggerFactory.getLogger(LoginService.class);

    private static final int expiryTime = 10;

    public User loginUser(String username, String password) {
        // sending same parameter two times because it can have either email or username
        User user = userService.getUserByUsernameOrEmail(username, username);
        if (user == null) {
            logger.error("User with Username: " + username +  " Not found!");
            throw new UserNotFoundException("No such user exists!");
        }
        else if(user.getActive() != true){
            return null;
        }
        else if (CryptoUtils.decrypt(user.getPassword()).equals(password)) {
            return user;
        } else {
            logger.warn("Incorrect password for username: " + username);
            throw new IncorrectPasswordException("Password is incorrect!");
        }
    }

    public Boolean forgotPassword(String username){
        User user = userService.getUserByUsernameOrEmail(username, username);
       if(user != null){
           boolean status;
           try{
               ForgotPassword forgotPassword = new ForgotPassword();
               forgotPassword.setUserId(user.getId());
               forgotPassword.setToken(UUID.randomUUID().toString());
               forgotPasswordRepository.save(forgotPassword);
               status = true;
               new Thread(() -> emailService.sendMail(user.getEmail(),
                       "Reset Password [LinkSharing@TTN]",
                       "http://localhost:8080/reset?token=" + forgotPassword.getToken())).start();
           }catch (MailException ex){
               logger.warn("Forget password mail sent failed!");
               status = false;
           }
           return status;
       }
       return false;
    }

    public ForgotPassword verifyResetToken(String token){
        ForgotPassword forgotPassword = forgotPasswordRepository.findByToken(token);
        if(forgotPassword == null){
            return null;
        }
        Long timeElapsed = getTimeElapsedInMinutes(forgotPassword.getCreatedOn(), LocalDateTime.now());
        if(timeElapsed > expiryTime){
            return null;
        }
        return forgotPassword;
    }

    public Boolean checkForAlreadyExistingToken(String username){
        User user = userService.getUserByUsernameOrEmail(username, username);
        if(user == null){
            return false;
        }
        ForgotPassword forgotPassword = forgotPasswordRepository.findByUserId(user.getId());
        if(forgotPassword == null){
            return false;
        }
        else{
            Long timeElapsed = getTimeElapsedInMinutes(forgotPassword.getCreatedOn(), LocalDateTime.now());;
            if(timeElapsed < expiryTime){
                return true;
            }
            else{
                deleteForgotPasswordToken(forgotPassword.getUserId());
                return false;
            }
        }

    }

    public void deleteForgotPasswordToken(Long userId){
        forgotPasswordRepository.delete(forgotPasswordRepository.findByUserId(userId));
    }

    public Long getTimeElapsedInMinutes(LocalDateTime time1, LocalDateTime time2){
        return MINUTES.between(time1, time2);
    }
}
