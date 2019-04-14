package com.ttn.linksharing.repositories;

import com.ttn.linksharing.entities.Rating;
import com.ttn.linksharing.entities.Resource;
import com.ttn.linksharing.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface RatingRepository extends CrudRepository<Rating, Long> {
    Rating findByUserAndResource(User user, Resource resource);
}
