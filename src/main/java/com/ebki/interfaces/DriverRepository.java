package com.ebki.interfaces;

import com.ebki.model.Driver;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DriverRepository extends CrudRepository<Driver, Long> {

    @Query(value = "SELECT * FROM driver WHERE email = :email", nativeQuery = true)
    Optional<Driver> selectDriverByEmail(
            @Param("email") String email);
}
