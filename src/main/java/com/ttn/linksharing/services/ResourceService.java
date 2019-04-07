package com.ttn.linksharing.services;

import com.ttn.linksharing.CO.DocumentResourceCO;
import com.ttn.linksharing.CO.LinkResourceCO;
import com.ttn.linksharing.controllers.FileUploadController;
import com.ttn.linksharing.entities.DocumentResource;
import com.ttn.linksharing.entities.LinkResource;
import com.ttn.linksharing.entities.User;
import com.ttn.linksharing.exceptions.UserNotFoundException;
import com.ttn.linksharing.repositories.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceService {
    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    TopicService topicService;

    @Autowired
    FileUploadController uploader;

    @Autowired
    UserService userService;

    public LinkResource createLinkResource(Long userId, LinkResourceCO linkResourceCO){
        if(userId != null){
            LinkResource linkResource = new LinkResource(linkResourceCO, topicService.getTopicById(linkResourceCO.getTopicId()));
            User user = userService.getUserById(userId);
            linkResource.setUser(user);
            return resourceRepository.save(linkResource);
        }else{
            throw new UserNotFoundException("No such user Exists!");
        }
    }

    public DocumentResource createDocumentResource(Long userId, DocumentResourceCO documentResourceCO){
        if(userId != null){
            DocumentResource documentResource = new DocumentResource(documentResourceCO, topicService.getTopicById(documentResourceCO.getTopicId()));
            User user = userService.getUserById(userId);
            documentResource.setUser(user);
            uploader.saveDocument(documentResourceCO.getDocument(), user.getUsername());
            return resourceRepository.save(documentResource);
        }else{
            throw new UserNotFoundException("No such user Exists!");
        }
    }
}
