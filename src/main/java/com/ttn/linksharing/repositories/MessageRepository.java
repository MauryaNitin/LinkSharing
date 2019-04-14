package com.ttn.linksharing.repositories;

import com.ttn.linksharing.entities.Message;
import com.ttn.linksharing.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {

    List<Message> findByUser(User user);

}
