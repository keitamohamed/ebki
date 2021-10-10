package com.ebki.repository;

import com.ebki.model.CarCheckIn;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CheckInRepo extends CrudRepository<CarCheckIn, Long> {

    @Query(value = "SELECT * FROM car_check_in WHERE car_vin_number = :vin_num", nativeQuery = true)
    Optional<CarCheckIn> findCarCheckInByCar_CarVinNumber(@Param("vin_num") Long aLong);

    @Query(value = "SELECT * FROM car_check_in", nativeQuery = true)
    List<CarCheckIn> findAll();

}
