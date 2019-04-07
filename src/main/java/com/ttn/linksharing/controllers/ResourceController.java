package com.ttn.linksharing.controllers;

import com.ttn.linksharing.CO.DocumentResourceCO;
import com.ttn.linksharing.CO.LinkResourceCO;
import com.ttn.linksharing.entities.DocumentResource;
import com.ttn.linksharing.entities.LinkResource;
import com.ttn.linksharing.services.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class ResourceController {
    @Autowired
    ResourceService resourceService;

    Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @PostMapping("/resources/link/create")
    public String createLinkResource(@Valid @ModelAttribute LinkResourceCO linkResourceCO,
                                     BindingResult result,
                                     HttpSession session){

        if(session.getAttribute("loggedInUserId") == null){
            logger.warn("Redirecting to Homepage, Request not Authorized.");
            return "redirect:/dashboard";
        }

        Long userId = (Long)session.getAttribute("loggedInUserId");
        if(result.hasErrors()){
            logger.warn(result.getFieldErrors().toString());
            return "redirect:/dashboard";
        }

        LinkResource resource = resourceService.createLinkResource(userId, linkResourceCO);
        if(resource == null){
            logger.error("Error occured in creating resource");
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/resources/document/create")
    public String createDocumentResource(@Valid @ModelAttribute DocumentResourceCO documentResourceCO,
                                         BindingResult result,
                                         HttpSession session){

        if(session.getAttribute("loggedInUserId") == null){
            logger.warn("Redirecting to Homepage, Request not Authorized.");
            return "redirect:/dashboard";
        }

        Long userId = (Long)session.getAttribute("loggedInUserId");
        if(result.hasErrors()){
            logger.warn(result.getFieldErrors().toString());
            return "redirect:/dashboard";
        }


        DocumentResource resource = resourceService.createDocumentResource(userId, documentResourceCO);
        if(resource == null){
            logger.error("Error occured in creating Document resource");
        }

        return "redirect:/dashboard";
    }
}
