package com.ebki.repository;

import com.ebki.model.CarCheckout;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CheckOutRepo extends CrudRepository<CarCheckout, Long> {

    @Query(value = "SELECT * FROM car_checkout WHERE car_vin_number = :vin_num", nativeQuery = true)
    Optional<CarCheckout> findCarCheckoutByCar_CarVinNumber(@Param("vin_num") Long aLong);

//    @Query(value = "SELECT * FROM car_checkout WHERE car_checkout.brand = :brand AND car_checkout.model = :model", nativeQuery = true)
//    List<CarCheckout> findCarCheckoutByCar_BrandAndCar_Model(
//            @Param("brand") String brand,
//            @Param("model") String model);
}
