package com.ttn.linksharing.repositories;

import com.ttn.linksharing.entities.Invitation;
import org.springframework.data.repository.CrudRepository;

public interface InvitationRepository extends CrudRepository<Invitation, Long> {
    Invitation findByToken(String token);
}
