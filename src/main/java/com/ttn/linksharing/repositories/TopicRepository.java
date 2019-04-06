package com.ttn.linksharing.repositories;

import com.ttn.linksharing.entities.Topic;
import com.ttn.linksharing.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends CrudRepository<Topic, Long> {
    List<Topic> findByUserId(Long userId);
}
