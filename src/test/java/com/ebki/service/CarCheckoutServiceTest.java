package com.ebki.service;

import com.ebki.model.Car;
import com.ebki.model.CarCheckout;
import com.ebki.repository.CheckOutRepo;
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

class CarCheckoutServiceTest {

    @Mock
    private CheckOutRepo repo;
    private CheckoutService carCheckoutService;
    @Captor
    private ArgumentCaptor<CarCheckout> argumentCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        carCheckoutService = new CheckoutService(repo);
    }

    @Test
    void itShouldCheckOutCar() {
        // Given
        long id = 672635511L;
        Car car = new Car(id, "Nissan", "Maxima", "Open Top", 2004);
        CarCheckout checkout = new CarCheckout();

        checkout.setCheckoutID(6738161L);
        checkout.setCar(car);
        // When
        given(repo.findById(checkout.getCheckoutID()))
                .willReturn(Optional.empty());
        // Then
        carCheckoutService.checkOutCar(checkout);
        then(repo).should().save(argumentCaptor.capture());
        CarCheckout capture = argumentCaptor.getValue();
        assertThat(capture).isEqualTo(checkout);
    }

    @Test
    void itShouldNotCheckOutCarWhenCarExists() {
        // Given
        long id = 672635511L;
        Car car = new Car(id, "Nissan", "Maxima", "Open Top", 2004);
        CarCheckout checkout = new CarCheckout();

        checkout.setCheckoutID(6728121L);
        checkout.setCar(car);

        given(repo.findById(checkout.getCheckoutID()))
                .willReturn(Optional.of(checkout));

        // When
        carCheckoutService.checkOutCar(checkout);
        // Then
        then(repo).should(never()).save(any());
    }

    @Test
    void itShouldThrowExceptionWhenCheckIDExists() {
        // Given
        long id = 672635511L;
        Car car = new Car(6271718L, "Nissan", "Maxima", "Open Top", 2004);
        Car carTwo = new Car(6271718L, "Nissan", "37Z", "Open Top", 2004);

        CarCheckout checkout = new CarCheckout();
        CarCheckout checkoutTwo = new CarCheckout();

        checkout.setCheckoutID(id);
        checkoutTwo.setCheckoutID(id);
        checkout.setCar(car);
        checkoutTwo.setCar(carTwo);

        given(repo.findById(checkout.getCheckoutID()))
                .willReturn(Optional.of(checkoutTwo));

        // When
        // Then
        assertThatThrownBy(() -> carCheckoutService.checkOutCar(checkout))
                .isInstanceOf(IllegalStateException.class)
                        .hasMessageContaining(String.format(
                                "Car [%s] with Vin number [%s] is already checkout ",
                                car.getBrand(), car.getCarVinNumber()));
        then(repo).should(never()).save(any(CarCheckout.class));
    }

    @Test
    void itShouldCheckOutCarWhenCheckOutIDIsNull() {
        // Given
        long id = 672635511L;
        Car car = new Car(id, "Nissan", "Maxima", "Open Top", 2004);

        CarCheckout checkout = new CarCheckout();
        checkout.setCar(car);
        checkout.setCheckoutID(null);

        given(repo.findCarCheckoutByCar_CarVinNumber(car.getCarVinNumber()))
                .willReturn(Optional.empty());
        // When
        carCheckoutService.checkOutCar(checkout);

        // Then
        then(repo).should().save(argumentCaptor.capture());
        CarCheckout checkout1 = argumentCaptor.getValue();
        assertThat(checkout1)
                .usingRecursiveComparison()
                .ignoringFields("name", "checkoutID")
                .isEqualTo(checkout);
        assertThat(checkout1.getCheckoutID()).isNotNull();
    }
}