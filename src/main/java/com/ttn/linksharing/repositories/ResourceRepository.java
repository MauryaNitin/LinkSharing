package com.ttn.linksharing.repositories;

import com.ttn.linksharing.entities.Resource;
import org.springframework.data.repository.CrudRepository;

public interface ResourceRepository extends CrudRepository<Resource, Long> {
}
