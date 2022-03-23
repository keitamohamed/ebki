package com.ebki.repository;

import com.ebki.model.Address;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AddressRepo extends CrudRepository<Address, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM address WHERE addressid = :id", nativeQuery = true)
    void deleteAddressByAddressID(@Param("id") Long id);
}
