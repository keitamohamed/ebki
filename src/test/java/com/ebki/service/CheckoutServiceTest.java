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

class CheckoutServiceTest {

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
        // Given...NEW CHECKOUT INFORMATION
        long id = 672635511L;
        Car car = new Car(id, "Nissan", "Maxima", "Open Top", 2004);
        CarCheckout checkout = new CarCheckout();

        checkout.setCheckoutID(6738161L);
        checkout.setCar(car);

        // When...CHECKOUT ID, IT SHOULD RETURN EMPTY OPTIONAL
        given(repo.findById(checkout.getCheckoutID()))
                .willReturn(Optional.empty());

        // Then...IT SHOULD SAVE CHECKOUT REQUEST
        carCheckoutService.checkOutCar(checkout);
        //...IT SHOULD SAVE THE REQUEST AND CAPTURE IT
        then(repo).should().save(argumentCaptor.capture());
        //...IT SHOULD RETURN THE CAPTURE VALUE
        CarCheckout capture = argumentCaptor.getValue();
        //...IT SHOULD CHECK THAT THE CAPTURE VALUE IS EQUAL TO THE ORIGINAL REQUEST
        assertThat(capture).isEqualTo(checkout);
    }

    @Test
    void itShouldNotCheckOutCarWhenCarExists() {
        // Given...NEW CHECKOUT INFORMATION
        long id = 672635511L;
        Car car = new Car(id, "Nissan", "Maxima", "Open Top", 2004);
        CarCheckout checkout = new CarCheckout();

        checkout.setCheckoutID(6728121L);
        checkout.setCar(car);

        //...Given CHECKOUT ID, IT SHOULD RETURN EMPTY OPTIONAL
        given(repo.findById(checkout.getCheckoutID()))
                .willReturn(Optional.of(checkout));

        // When...IT SHOULD NOT SAVE THE CHECKOUT REQUEST SINCE THE CHECKOUT ALREADY EXIST
        carCheckoutService.checkOutCar(checkout);
        // Then...IT SHOULD CHECK THAT THE CHECKOUT REQUEST WAS NOT SAVE
        then(repo).should(never()).save(any());
    }

    @Test
    void itShouldThrowExceptionWhenCheckIDExists() {
        // Given...NEW CHECKOUT INFORMATION
        long id = 672635511L;
        Car car = new Car(6271718L, "Nissan", "Maxima", "Open Top", 2004);
        Car carTwo = new Car(6271718L, "Nissan", "37Z", "Open Top", 2004);

        CarCheckout checkout = new CarCheckout();
        CarCheckout checkoutTwo = new CarCheckout();

        checkout.setCheckoutID(id);
        checkoutTwo.setCheckoutID(id);
        checkout.setCar(car);
        checkoutTwo.setCar(carTwo);

        //...IT SHOULD RETURN CHECKOUT WITH GIVEN ID THAT IS SAME AS CHECKOUT TWO
        given(repo.findById(checkout.getCheckoutID()))
                .willReturn(Optional.of(checkoutTwo));

        // When
        // Then ...IT SHOULD THROW ILLEGAL STATE EXCEPTION SINCE GIVEN ID ALREADY EXISTS
        assertThatThrownBy(() -> carCheckoutService.checkOutCar(checkout))
                .isInstanceOf(IllegalStateException.class)
                        .hasMessageContaining(String.format(
                                "Car [%s] with Vin number [%s] is already checkout ",
                                car.getBrand(), car.getCarVinNumber()));
        //...IT SHOULD CHECK THAT THE REQUEST WAS NOT SAVE
        then(repo).should(never()).save(any(CarCheckout.class));
    }

    @Test
    void itShouldCheckOutCarWhenCheckOutIDIsNull() {
        // Given...NEW CHECKOUT INFORMATION
        long id = 672635511L;
        Car car = new Car(id, "Nissan", "Maxima", "Open Top", 2004);

        CarCheckout checkout = new CarCheckout();
        checkout.setCar(car);
        checkout.setCheckoutID(null);

        //...GIVEN THE CAR VIN NUMBER, IT SHOULD RETURN AN EMPTY OPTIONAL OF CHECKOUT
        given(repo.findCarCheckoutByCar_CarVinNumber(car.getCarVinNumber()))
                .willReturn(Optional.empty());
        // When...IT SHOULD SAVE THE CHECKOUT REQUEST
        carCheckoutService.checkOutCar(checkout);

        // Then...IT SAVE THE REQUEST AND CAPTURE THE REQUEST
        then(repo).should().save(argumentCaptor.capture());
        //...IT SHOULD RETURN THE CAPTURE VALUE
        CarCheckout captureValue = argumentCaptor.getValue();
        assertThat(captureValue)
                .usingRecursiveComparison()
                .ignoringFields("name", "checkoutID")
                .isEqualTo(checkout);
        //...IT SHOULD CHECK THAT CAPTURE VALUE IS NOT NULL
        assertThat(captureValue.getCheckoutID()).isNotNull();
    }
}