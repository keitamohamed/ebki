package com.ebki.repository;

import com.ebki.model.Car;
import com.ebki.model.CarCheckIn;
import com.ebki.model.Driver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DataJpaTest(
        properties = {
                "spring.jpa.properties.javax.persistence.validation.mode=none"
        }
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CheckInRepoTest {

    @Autowired
    private CheckInRepo repo;

    @Test
    void itShouldNotSelectCheckInWhenCheckInIDDoesNotExists() {
        // Given
        long checkInID = 5273181L;

        // When
        Optional<CarCheckIn> optional = repo.findById(checkInID);
        // Then
        assertThat(optional).isNotPresent();
    }

    @Test
    void itShouldSelectCarCheckInByCarBrandAndModel() {
        // Given
        CarCheckIn checkIn = new CarCheckIn();

        Car car = new Car(536118L, "Toyota", "RAV4", "SUV", 2021);
        checkIn.setCheckInID(514241L);
        checkIn.setCar(car);

        // When
        repo.save(checkIn);

        // Then
        Optional<CarCheckIn> optional = repo.findById(checkIn.getCheckInID());
        assertThat(optional)
                .isPresent()
                .hasValueSatisfying(check -> {
                    assertThat(check.getCar())
                            .usingRecursiveComparison()
                            .isEqualTo(checkIn.getCar());
                });
    }

    @Test
    void itShouldNotCheckInWhenAFieldIsNull() {
        // Given
        CarCheckIn checkIn = new CarCheckIn();
        Car car = new Car(6351619L, null, "RAV4", "SUV", 2021);

        checkIn.setCheckInID(6838319L);
        checkIn.setCar(car);

        // When
        // Then
        assertThatThrownBy(() -> repo.save(checkIn))
                .hasMessageContaining("not-null property references a null or transient value :")
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void itShouldFindCarCheckInByCarBrandAndModel() {
        // Given
        CarCheckIn checkIn = new CarCheckIn();
        Car car = new Car(6351619L, "Toyota", "RAV4", "SUV", 2021);
        Driver driver = new Driver();
//        Driver driver = new Driver(7823516L, "Mohamed", "Keita", "keitamohamed12@gmail.com", "5405666378");

        checkIn.setCheckInID(6838319L);
        checkIn.setCarCheckIn(driver);
        checkIn.setCar(car);
        // When....save car check in...
        repo.save(checkIn);

        // Then...will find car check in by car vin num
        Optional<CarCheckIn> optional = repo.findCarCheckInByCar_CarVinNumber(car.getVin());
        assertThat(optional)
                .isPresent()
                .hasValueSatisfying(checkIn1 -> {
                    assertThat(checkIn1)
                            .usingRecursiveComparison()
                            .isEqualTo(checkIn);
                });
    }
}