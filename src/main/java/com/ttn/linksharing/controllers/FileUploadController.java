package com.ttn.linksharing.controllers;


import com.ttn.linksharing.CO.SignupCO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class FileUploadController {
    private Logger logger = LoggerFactory.getLogger(FileUploadController.class);
    public boolean saveProfilePicture(MultipartFile photo, SignupCO signupCO){
        try {
            Path path = Paths.get(System.getProperty("user.dir") + "/src/main/resources/static/images/" + signupCO.getUsername() + "_" + photo.getOriginalFilename());
            byte[] profilePic = photo.getBytes();
            Files.write(path, profilePic);
        } catch (IOException e) {
            logger.warn("file upload failed!" + photo.getOriginalFilename());
        }
        return true;
    }
}
