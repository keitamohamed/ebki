package com.ebki.interfaces;

import com.ebki.model.CarCheckIn;
import org.springframework.data.repository.CrudRepository;

public interface CheckInRepository extends CrudRepository<CarCheckIn, Long> {
}
