package com.ebki.service;

import com.ebki.repository.CarRepo;
import com.ebki.model.Car;
import com.ebki.request.CarRegistrationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;


class CarServiceTest {

    @Mock
    private CarRepo carRepository;

    @Captor
    private ArgumentCaptor<Car> carArgumentCaptor;

    private CarService carServiceTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        carServiceTest = new CarService(carRepository);
    }

    @Test
    void itShouldSaveCar() {
        // Given
        long id = 672635511L;
        Car car = new Car(id, "Nissan", "Maxima", "Open Top", 2004);
        Optional<Car> optionalCar = carRepository.findById(id);

        // When
        given(carRepository.selectCarByVinNumber(car.getCarVinNumber()))
                .willReturn(Optional.empty());

        // Then
        carServiceTest.registerNewCar(car);
        then(carRepository).should().save(carArgumentCaptor.capture());
        Car capture = carArgumentCaptor.getValue();
        assertThat(capture).isEqualTo(car);
    }

    @Test
    void itShouldSaveCarWhenVinNumIsNull() {
        // Given
        long id = 672635511L;
        Car car = new Car(null, "Nissan", "Maxima", "Open Top", 2004);

        // ... a request
        CarRegistrationRequest request = new CarRegistrationRequest(car);

        // ... No car with vin number
        given(carRepository.selectCarByVinNumber(id))
                .willReturn(Optional.empty());
        // When
        carServiceTest.registerNewCar(request.getCar());

        // Then...It will not save the car when the vin number already exists
        then(carRepository).should().save(carArgumentCaptor.capture());
        Car capture = carArgumentCaptor.getValue();
        assertThat(capture)
                .usingRecursiveComparison()
                .ignoringFields("name", "carVinNumber")
                .isEqualTo(car);
//                .isEqualToIgnoringGivenFields(car, "carVinNumber");

        assertThat(capture.getCarVinNumber()).isNotNull();
    }

    @Test
    void itShouldNotSaveCarWhenCarExists() {
        // Given
        long id = 672635511L;
        Car car = new Car(id, "Nissan", "Maxima", "Open Top", 2004);

        // ...an existing car returned
        given(carRepository.selectCarByVinNumber(car.getCarVinNumber()))
                .willReturn(Optional.of(car));
        // When
        carServiceTest.registerNewCar(car);

        // Then
        then(carRepository).should(never()).save(any());
    }

    @Test
    void itShouldThrowExceptionWhenVinNumExists() {
        // Given
        long vinNum = 782341672L;
        Car car1 = new Car(vinNum, "Nissan", "Maxima", "Open Top", 2004);
        Car car2 = new Car(vinNum, "Nissan", "370Z", "Close Top", 2012);

        CarRegistrationRequest carRegistrationRequest = new CarRegistrationRequest(car1);
        given(carRepository.selectCarByVinNumber(car1.getCarVinNumber()))
                .willReturn(Optional.of(car2));
        // When
        // Then
        assertThatThrownBy(() -> carServiceTest.registerNewCar(carRegistrationRequest.getCar()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format("Vin number [%s] is taken", car1.getCarVinNumber()));
        // finally
        then(carRepository).should(never()).save(any(Car.class));
    }
}