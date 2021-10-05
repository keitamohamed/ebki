package com.ebki.repository;

import com.ebki.model.CarCheckIn;
import org.springframework.data.repository.CrudRepository;

public interface CheckInRepo extends CrudRepository<CarCheckIn, Long> {
}
