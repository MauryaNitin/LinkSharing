package com.ttn.linksharing.services;

import com.ttn.linksharing.CO.DocumentResourceCO;
import com.ttn.linksharing.CO.LinkResourceCO;
import com.ttn.linksharing.controllers.FileUploadController;
import com.ttn.linksharing.entities.*;
import com.ttn.linksharing.enums.Visibility;
import com.ttn.linksharing.exceptions.UserNotFoundException;
import com.ttn.linksharing.repositories.MessageRepository;
import com.ttn.linksharing.repositories.RatingRepository;
import com.ttn.linksharing.repositories.ResourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResourceService {
    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    TopicService topicService;

    @Autowired
    FileUploadController uploader;

    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

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

    public Resource getResourceById(Long resourceId){
        Optional<Resource> optionalResource =  resourceRepository.findById(resourceId);
        Resource resource = optionalResource.orElse(null);
        if(resource == null){
            logger.warn("Resource not found!");
        }
        return resource;
    }

    public List<Resource> searchResoucesByDescription(String query, Long userId){
        return resourceRepository.findByDescriptionLike("%" + query + "%")
                .stream()
                .filter(x -> (x.getTopic().getVisibility() == Visibility.PRIVATE && x.getUser().getId() != userId))
                .collect(Collectors.toList());
    }

    public List<Resource> getTopPosts(Integer count){
        return resourceRepository
                .getTopPostsHavingMaxRatings(new PageRequest(0, 5))
                .getContent()
                .stream().filter(x -> x.getTopic().getVisibility() != Visibility.PRIVATE)
                .collect(Collectors.toList());
    }

    public Message markResourceAsRead(Long resourceId, Long userId){
        Message message = new Message();
        User user = userService.getUserById(userId);
        Resource resource = getResourceById(resourceId);
        message.setUser(user);
        message.setResource(resource);
        return messageRepository.save(message);
    }

//    public List<Resource> getInboxResources(Long userId){
//        User user = userService.getUserById(userId);
//        List<Resource> readResources = messageRepository.findByUser(user).stream().map(Message::getResource).collect(Collectors.toList());
//        return resourceRepository
//                .getSubscribedResourcesOfUser(user)
//                .stream()
//                .filter(x->!readResources.contains(x))
//                .collect(Collectors.toList());
//    }

    public Rating getRatingOfResourceOfUser(Long userId, Long resourceId){
        Resource resource = getResourceById(resourceId);
        User user = userService.getUserById(userId);
       return ratingRepository.findByUserAndResource(user, resource);
    }

    public Rating rateResource(Long userId, Long resourceId, Integer rate){
        Rating rating = getRatingOfResourceOfUser(userId, resourceId);
        rating.setRating(rate);
        return ratingRepository.save(rating);
    }

    public Resource updateResourceDescription(Long resourceId, String description){
        Resource resource = getResourceById(resourceId);
        resource.setDescription(description);
        return resourceRepository.save(resource);
    }

    public void deleteResource(Long resourceId){
        Resource resource = getResourceById(resourceId);
        resourceRepository.delete(resource);
    }
}
