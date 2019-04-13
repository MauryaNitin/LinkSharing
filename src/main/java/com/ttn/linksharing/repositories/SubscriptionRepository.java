package com.ttn.linksharing.repositories;

import com.ttn.linksharing.entities.Subscription;
import com.ttn.linksharing.entities.Topic;
import com.ttn.linksharing.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {

//    @Query(value = "SELECT t FROM subscription s INNER JOIN topic t ON s.topic_id = t.topic_id WHERE s.user_id = :user_id", nativeQuery = true)
//    List<Object[]> findTopicsByUserId(@Param("user_id") User user);

//    @Query("select s from Subscription s where user_id = :userId")
//    List<Subscription> getSubscriptionByUserId(@Param("userId") Long userId);

//    @Query("select topic_id from Subscription s where user_id = :userId")
//    List<Long> getTopicIdsOfSubscriptionsByUserId(@Param("userId") Long userId);

    List<Subscription> findByUser(User user);
    Integer deleteByUserAndTopic(User user, Topic topic);
    Integer deleteByUser_IdAndTopic_Id(Long userId, Long topicId);

}
