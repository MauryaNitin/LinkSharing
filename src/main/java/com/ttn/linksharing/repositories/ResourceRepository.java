package com.ttn.linksharing.repositories;

import com.ttn.linksharing.entities.Resource;
import com.ttn.linksharing.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends CrudRepository<Resource, Long> {

    List<Resource> findByDescriptionLike(String query);

    @Query("SELECT resource FROM Resource resource LEFT OUTER JOIN resource.rating rate  GROUP BY resource.id ORDER BY count(rate.id) desc")
    Page<Resource> getTopPostsHavingMaxRatings(Pageable pageable);

//    @Query("select resource from Resource resource left join resource.messages m where m.id is null and resource.user = :user")
//    Page<Resource> getInboxResourcesOfUser(Pageable pageable, @Param("user") User userId);

//    @Query("select r from Resource r inner join r.topic. where s.user = :user")
//    List<Resource> getSubscribedResourcesOfUser(@Param("user") User user);

}
