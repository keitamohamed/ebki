package com.ebki.interfaces;

import com.ebki.model.CarCheckout;
import org.springframework.data.repository.CrudRepository;

public interface CheckOutRepository extends CrudRepository<CarCheckout, Long> {
}
