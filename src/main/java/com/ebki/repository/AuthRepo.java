package com.ebki.repository;

import com.ebki.model.Authenticate;
import org.springframework.data.repository.CrudRepository;

public interface AuthRepo extends CrudRepository<Authenticate, Long> {

    Authenticate findByUsername(String username);
}
