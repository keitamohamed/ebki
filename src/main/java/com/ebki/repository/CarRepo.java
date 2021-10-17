package com.ebki.repository;

import com.ebki.model.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CarRepo extends CrudRepository<Car, Long> {

   @Query(value = "SELECT * FROM car WHERE brand = :brand AND year = :year", nativeQuery = true)
    Optional<Car> findCarByBrandAndModelYear(@Param("brand") String brand, @Param("year") int year);

    @Query(value = "SELECT * FROM car WHERE vin = :id", nativeQuery = true)
    Optional<Car> selectCarByVinNumber(@Param("id") Long aLong);

    Optional<Car> findCarByVin(@Param("vin") Long carVinNumber);

    List<Car> findCarByBrand(@Param("brand") String brand);

    @Query(value = "SELECT * FROM car WHERE brand = :brand " +
            "AND model = :model AND year = :year", nativeQuery = true)
    List<Car> findCarByBrandAndModelAndModelYear(
            @Param("brand") String brand,
            @Param("model") String model,
            @Param("year") int year);
}
