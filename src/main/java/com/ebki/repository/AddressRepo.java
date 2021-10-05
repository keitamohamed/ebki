package com.ebki.repository;

import com.ebki.model.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepo extends CrudRepository<Address, Long> {

}
