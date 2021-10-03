package com.ebki.interfaces;

import com.ebki.model.Car;
import com.ebki.request.CarRegistrationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;

@DataJpaTest (
        properties = {
                "spring.jpa.properties.javax.persistence.validation.mode=none"
        }
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @Test
    void itShouldNotSelectCarWhenVinNumDoesNotExists() {
        // Given
        long vinNum = 782341654L;
        // When
        Optional<Car> optionalCar = carRepository.selectCarByVinNumber(vinNum);
        // Then
        assertThat(optionalCar).isNotPresent();
    }

    @Test
    void itShouldSaveCar() {
        // Given
        Car car = new Car(672635511L, "Nissan", "Maxima", "Open Top", 2004);
        CarRegistrationRequest request = new CarRegistrationRequest(car);
        // When
        carRepository.save(request.getCar());
        // Then
        Optional<Car> optionalCar = carRepository.selectCarByVinNumber(car.getCarVinNumber());
        assertThat(optionalCar)
                .isPresent()
                .hasValueSatisfying(c -> {
                    assertThat(c)
                            .usingRecursiveComparison()
                            .isEqualTo(car);
                });
    }

    @Test
    void itShouldFindCarByBrandAndModelYear() {
        // Given
        long id = 672635511L;
        Car car = new Car(id, "Nissan", "Maxima", "Open Top", 2004);
        CarRegistrationRequest request = new CarRegistrationRequest(car);
        // When
        carRepository.save(request.getCar());
        // Then
        Optional<Car> optionalCar = carRepository.findCarByBrandAndModelYear(car.getBrand(), car.getModelYear());
        assertThat(optionalCar)
                .isPresent()
                .hasValueSatisfying(c -> {
                    assertThat(c)
                            .usingRecursiveComparison()
                            .isEqualTo(car);

                });

    }

    @Test
    void itShouldSelectCarByBrandAndModelAndModelYear() {
        // Given
        Car car = new Car(67231631L, "Nissan", "Maxima", "Open Top", 2004);
        CarRegistrationRequest request = new CarRegistrationRequest(car);
        // When
        carRepository.save(request.getCar());
        // Then
        List<Car> optionalCar = carRepository.findCarByBrandAndModelAndModelYear(car.getBrand(), car.getModel(), car.getModelYear());
        assertThat(optionalCar)
                .isNotNull()
                .asList()
                .hasSizeGreaterThan(0);
    }

    @Test
    void itShouldNotSaveCarWhenBrandIsNull() {
        // Given
        Car car = new Car(672635511L, null, "Maxima", "Open top", 2004);
        CarRegistrationRequest request = new CarRegistrationRequest(car);
        // When
        // Then
        assertThatThrownBy(() -> carRepository.save(request.getCar()))
                .hasMessageContaining("not-null property references a null or transient value :")
                .isInstanceOf(DataIntegrityViolationException.class);

    }
}