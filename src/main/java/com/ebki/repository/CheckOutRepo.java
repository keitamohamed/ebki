package com.ebki.repository;

import com.ebki.model.CarCheckout;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CheckOutRepo extends CrudRepository<CarCheckout, Long> {

    @Query(value = "SELECT * FROM car_checkout WHERE vin = :vin_num", nativeQuery = true)
    Optional<CarCheckout> findCarCheckoutByCar_CarVinNumber(@Param("vin_num") Long aLong);

    @Modifying
    @Query(value = "DELETE FROM car_checkout WHERE checkoutid = :id", nativeQuery = true)
    int deleteByCheckoutID(@Param("id") Long id);
}
