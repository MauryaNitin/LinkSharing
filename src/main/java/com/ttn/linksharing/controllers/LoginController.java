package com.ttn.linksharing.controllers;

import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Map<String, String> credentials, HttpServletRequest request, HttpServletResponse response){
        User user = loginService.loginUser(credentials);
        HttpSession session = request.getSession();
        session.setAttribute("logged-in-user", user);
        return ResponseEntity.accepted().build();
    }
}
