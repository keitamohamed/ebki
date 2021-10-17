package com.ebki.repository;

import com.ebki.model.Car;
import com.ebki.model.CarCheckout;
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
class CheckOutRepoTest {

    @Autowired
    private CheckOutRepo repository;

    @Test
    void itShouldNotSelectCheckoutWhenCheckoutIDDoesNotExists() {
        // Given
        long checkoutId = 6351511L;
        // When
        Optional<CarCheckout> checkout = repository.findById(checkoutId);
        // Then
        assertThat(checkout).isNotPresent();
    }

    @Test
    void itShouldSaveCarCheckingOut() {
        // Given
        Car car = new Car(672635511L, "Nissan", "Maxima", "Open Top", 2004);
        CarCheckout checkout = new CarCheckout();

        car.setYear(2004);
        checkout.setCheckoutID(67251426L);
        checkout.setCar(car);

        // When
        repository.save(checkout);

        // Then
        Optional<CarCheckout> optional = repository.findById(checkout.getCheckoutID());
        assertThat(optional)
                .isPresent()
                .hasValueSatisfying(c -> {
                    assertThat(c)
                            .usingRecursiveComparison()
                            .isEqualTo(checkout);
                });
    }

    @Test
    void itShouldNotSaveCheckOutWhenCarFieldIsNull() {
        // Given
        Car car = new Car(672635511L, null, "Maxima", "Open top", 2004);
        CarCheckout checkout = new CarCheckout();

        checkout.setCheckoutID(56729182L);
        checkout.setCar(car);
        // When
        // Then
        assertThatThrownBy(() -> repository.save(checkout))
                .hasMessageContaining("not-null property references a null or transient value :")
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void itShouldSelectCheckOutByCarVinNum() {
        // Given
        CarCheckout checkout = new CarCheckout();

        checkout.setCheckoutID(67251426L);
        checkout.setCar(new Car(672635511L, "Nissan", "Maxima", "Open Top", 2004));

        // When
        repository.save(checkout);

        // Then
        Optional<CarCheckout> optional = repository.findCarCheckoutByCar_CarVinNumber(checkout.getCar().getVin());
        assertThat(optional)
                .isPresent()
                .hasValueSatisfying(checkout1 -> {
                    assertThat(checkout1)
                            .usingRecursiveComparison()
                            .isEqualTo(checkout);
                });
    }
}