package com.ebki.repository;

import com.ebki.model.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CarRepo extends CrudRepository<Car, Long> {

   @Query(value = "SELECT * FROM car WHERE brand = :brand AND model_year = :year", nativeQuery = true)
    Optional<Car> findCarByBrandAndModelYear(@Param("brand") String brand, @Param("year") int year);

    @Query(value = "SELECT * FROM car WHERE car_vin_number = :id", nativeQuery = true)
    Optional<Car> selectCarByVinNumber(@Param("id") Long aLong);

    Optional<Car> findCarByCarVinNumber(@Param("carVinNumber") Long carVinNumber);

    Optional<Car> findCarByBrand(@Param("brand") String brand);

    @Query(value = "SELECT * FROM car WHERE brand = :brand " +
            "AND model = :model AND model_year = :year", nativeQuery = true)
    List<Car> findCarByBrandAndModelAndModelYear(
            @Param("brand") String brand,
            @Param("model") String model,
            @Param("year") int year);
}
