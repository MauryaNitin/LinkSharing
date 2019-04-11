package com.ttn.linksharing.repositories;

import com.ttn.linksharing.entities.ForgotPassword;
import org.springframework.data.repository.CrudRepository;

public interface ForgotPasswordRepository extends CrudRepository<ForgotPassword, Long> {
    ForgotPassword findByToken(String token);
    ForgotPassword findByUserId(Long userId);
}
