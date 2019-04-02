package com.ttn.linksharing.repositories;

import com.ttn.linksharing.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
    User findByUsernameOrEmail(String username, String email);
    Boolean existsByUsernameOrEmail(String username, String email);
}
