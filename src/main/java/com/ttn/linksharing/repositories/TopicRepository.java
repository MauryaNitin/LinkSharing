package com.ttn.linksharing.repositories;

import com.ttn.linksharing.entities.Topic;
import com.ttn.linksharing.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends CrudRepository<Topic, Long> {
    List<Topic> findByUserId(Long userId);

    @Query("SELECT t FROM Topic t LEFT OUTER JOIN t.resourcesList r group by t.id ORDER BY COUNT (r.id) desc")
    Page<Topic> getTrendingTopics(Pageable pageable);

    List<Topic> findByNameLike(String query);

    Topic findFirstByName(String name);

    Topic findFirstByNameAndUser_Id(String name, Long userId);

    Topic deleteByUser_IdAndAndId(Long userId, Long topicId);

    Integer deleteTopicByIdAndUser_Id(Long topicId, Long userId);
}
