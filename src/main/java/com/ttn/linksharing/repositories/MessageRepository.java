package com.ttn.linksharing.repositories;

import com.ttn.linksharing.entities.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {

}
