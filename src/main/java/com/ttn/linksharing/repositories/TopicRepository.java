package com.ttn.linksharing.repositories;

import com.ttn.linksharing.entities.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface TopicRepository extends CrudRepository<Topic, Long> {
    List<Topic> findByUserId(Long userId);

    @Query("SELECT t FROM Topic t LEFT OUTER JOIN t.resourcesList r group by t.id ORDER BY COUNT (r.id) desc")
    Page<Topic> getTrendingTopics(Pageable pageable);
}
