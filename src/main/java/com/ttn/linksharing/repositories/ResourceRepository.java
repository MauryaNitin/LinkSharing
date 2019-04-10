package com.ttn.linksharing.repositories;

import com.ttn.linksharing.entities.Resource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends CrudRepository<Resource, Long> {

    List<Resource> findByDescriptionLike(String query);

}
