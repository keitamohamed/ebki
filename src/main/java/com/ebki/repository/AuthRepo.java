package com.ebki.repository;

import com.ebki.model.Authenticate;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AuthRepo extends CrudRepository<Authenticate, Long> {
    Authenticate findByUsername(String username);
}
