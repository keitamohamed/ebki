package com.ebki.service;

import com.ebki.TestData;
import com.ebki.model.CarCheckIn;
import com.ebki.model.CarCheckout;
import com.ebki.repository.CarRepo;
import com.ebki.model.Car;
import com.ebki.request.CarRegistration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;


class CarServiceTest {

    @Mock
    private CarRepo repository;
    @Captor
    private ArgumentCaptor<Car> argumentCaptor;
    private CarService service;
    private final TestData data = new TestData();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new CarService(repository);
    }

    @Test
    void itShouldSaveCar() {
        // Given
        long id = 672635511L;
        Car car = new Car(id, "Nissan", "Maxima", "Open Top", 2004);
        Optional<Car> optionalCar = repository.findById(id);

        // When
        given(repository.selectCarByVinNumber(car.getVin()))
                .willReturn(Optional.empty());

        // Then
        service.save(car);
        then(repository).should().save(argumentCaptor.capture());
        Car capture = argumentCaptor.getValue();
        assertThat(capture).isEqualTo(car);
    }

    @Test
    void itShouldSaveCarWhenVinNumIsNull() {
        // Given
        long id = 672635511L;
        Car car = new Car(null, "Nissan", "Maxima", "Open Top", 2004);

        // ... a request
        CarRegistration request = new CarRegistration(car);

        // ... No car with vin number
        given(repository.selectCarByVinNumber(id))
                .willReturn(Optional.empty());
        // When
        service.save(request.getCar());

        // Then...It will not save the car when the vin number already exists
        then(repository).should().save(argumentCaptor.capture());
        Car capture = argumentCaptor.getValue();
        assertThat(capture)
                .usingRecursiveComparison()
                .ignoringFields("name", "carVinNumber")
                .isEqualTo(car);
//                .isEqualToIgnoringGivenFields(car, "carVinNumber");

        assertThat(capture.getVin()).isNotNull();
    }

    @Test
    void itShouldNotSaveCarWhenCarExists() {
        // Given
        long id = 672635511L;
        Car car = new Car(id, "Nissan", "Maxima", "Open Top", 2004);

        // ...an existing car returned
        given(repository.selectCarByVinNumber(car.getVin()))
                .willReturn(Optional.of(car));
        // When
        service.save(car);

        // Then
        then(repository).should(never()).save(any());
    }

    @Test
    void itShouldThrowExceptionWhenVinNumExists() {
        // Given...CAR INFORMATION
        long vinNum = 782341672L;
        Car car1 = new Car(vinNum, "Nissan", "Maxima", "Open Top", 2004);
        Car car2 = new Car(vinNum, "Nissan", "370Z", "Close Top", 2012);

        CarRegistration carRegistrationRequest = new CarRegistration(car1);

        //...IT SHOULD RETURN CAR WITH VAIN NUMBER AS SECOND CAR
        given(repository.selectCarByVinNumber(car1.getVin()))
                .willReturn(Optional.of(car2));
        // When
        // Then...IT SHOULD THROW ILLEGAL STATE EXCEPTION
        assertThatThrownBy(() -> service.save(carRegistrationRequest.getCar()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format("Vin number [%s] is taken", car1.getVin()));
        // finally...IT SHOULD NOT SAVE THE CAR
        then(repository).should(never()).save(any(Car.class));
    }

    @Test
    void itShouldFindCarByBrand() {
        // Given...GIVEN CARS LIST
        List<Car> carList = data.carList();
        // When...IT SHOULD SAVE LIST OF CAR
        carList.forEach(car -> {
            service.save(car);
        });
        Car car = carList.get(carList.size() - 1);
        // Then...IT SHOULD CHECK NUMBER OF CARS SAVE IS THE SAME AS CAR_LIST_SIZE
        //...AND IT SHOULD CAPTURE THE CARS SAVE
        verify(repository, times(carList.size())).save(argumentCaptor.capture());
        //...IT SHOULD RETURN THE CARS LIST
        List<Car> listCar = argumentCaptor.getAllValues();
        //...IT SHOULD FIND CARS BY GIVEN BRAND, LIST OF CARS RETURN SHOULD
        //...NOT BE NULL AND CONTAINS ONLY CARS WITH BRAND OF TOYOTA
        assertThat(service.findCarByBand(listCar, car.getBrand()))
                .isNotNull()
                .asList()
                .hasSizeGreaterThan(1)
                .satisfies(cars -> {
                    assertThat(cars.get(0))
                            .usingRecursiveComparison()
                            .isEqualTo(carList.get(carList.size() - 2));
                });

    }

    @Test
    void itShouldFindCarByYear() {
        // Given...GIVEN CARS LIST
        List<Car> carList = data.carList();
        // When...IT SHOULD SAVE LIST OF CAR
        carList.forEach(car -> {
            service.save(car);
        });
        Car car = carList.get(carList.size() - 1);
        // Then...IT SHOULD CHECK NUMBER OF CARS SAVE IS THE SAME AS CAR_LIST_SIZE
        //...AND IT SHOULD CAPTURE THE CARS SAVE
        verify(repository, times(carList.size())).save(argumentCaptor.capture());
        //...IT SHOULD RETURN THE CARS LIST
        List<Car> listCar = argumentCaptor.getAllValues();
        //...IT SHOULD FIND CARS BY GIVEN YEAR, LIST OF CARS RETURN SHOULD
        //...NOT BE NULL AND CONTAINS ONLY CARS WITH YEAR OF 2016
        assertThat(service.findCarByYear(listCar, car.getYear()))
                .isNotNull()
                .asList()
                .hasSize(2)
                .satisfies(cars -> {
                    assertThat(cars.get(0))
                            .usingRecursiveComparison()
                            .isEqualTo(carList.get(carList.size() - 3));
                });
    }

    @Test
    void itShouldThrowExceptionForFindCarByBrandWhenListIsEmpty() {
        // Given
        Car car = data.carList().get(3);
        List<Car> list = new ArrayList<>();
        // When

        // Then
        //...IT SHOULD THROW EXCEPTION, SINCE EMPTY LATS IS BEING PASS
        assertThatThrownBy(() -> service.findCarByBand(list, car.getBrand()))
                .hasMessageContaining(String.format("Car list [ %s ] is empty", 0))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void itShouldThrowExceptionForFindCarByBrandWhenBrandIsEmpty() {
        // Given
        Car car = new Car();
        car.setBrand("");
        List<Car> list = data.carList();
        // When

        // Then
        //...IT SHOULD THROW EXCEPTION, SINCE Brand IS EMPTY
        assertThatThrownBy(() -> service.findCarByBand(list, car.getBrand()))
                .hasMessageContaining(String.format("Car brand [ %s ] cannot be null or empty", car.getBrand()))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void itShouldThrowExceptionWhenCarListIsEmpty() {
        // Given
        Car car = data.carList().get(3);
        List<Car> list = new ArrayList<>();
        // When

        // Then
        //...IT SHOULD THROW EXCEPTION, SINCE EMPTY LATS IS BEING PASS
        assertThatThrownBy(() -> service.findCarByBand(list, car.getBrand()))
                .hasMessageContaining(String.format("Car list [ %s ] is empty", 0))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void itShouldShouldThrowExceptionForFindCarByYearWhenYearIsZero() {
        // Given...CAR INFORMATION
        Car car = new Car();
        List<Car> list = data.carList();
        // When

        // Then
        //...IT SHOULD THROW EXCEPTION, SINCE YEAR IS EMPTY
        assertThatThrownBy(() -> service.findCarByYear(list, car.getYear()))
                .hasMessageContaining(String.format("Car year [ %s ] cannot be 0 or empty", car.getYear()))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void itShouldShouldThrowExceptionForFindCarByYearWhenListIsEmpty() {
        // Given...CAR INFORMATION
        Car car = data.carList().get(2);
        List<Car> list = new ArrayList<>();
        // When

        // Then
        //...IT SHOULD THROW EXCEPTION, SINCE YEAR IS EMPTY
        assertThatThrownBy(() -> service.findCarByYear(list, car.getYear()))
                .hasMessageContaining(String.format("Car list is [ %s ]. No cars in database", car.getYear()))
                .isInstanceOf(IllegalStateException.class);
    }
}