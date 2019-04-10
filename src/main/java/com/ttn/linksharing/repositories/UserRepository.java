package com.ttn.linksharing.repositories;

import com.ttn.linksharing.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
    User findByUsernameOrEmail(String username, String email);
    Boolean existsByUsernameOrEmail(String username, String email);

    @Query("select u from User u")
    List<User> findAllUsers();
}
