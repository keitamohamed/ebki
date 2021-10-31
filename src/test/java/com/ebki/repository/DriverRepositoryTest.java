package com.ebki.repository;

import com.ebki.model.Driver;
import com.ebki.request.DriverRegistration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DataJpaTest (
        properties = {
                "spring.jpa.properties.javax.persistence.validation.mode=none"
        }
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DriverRepositoryTest {

    @Autowired
    private DriverRepo underTest;

    String dob = "12-17-2004";
    SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");

    @Test
    void itShouldFindDriverByID() throws ParseException {
        // Given...Driver information
        long id = 7823516L;
        Driver driver = new Driver(id, "Mohamed", "Keita", "keitamohamed12@gmail.com", "5405666378");
        driver.setDob(format.parse(dob));
        driver.setGender("Male");

        // When
        underTest.save(driver);
        // Then
        Optional<Driver> optionalDriver = underTest.findById(id);
        assertThat(optionalDriver)
                .isPresent()
                .hasValueSatisfying(d -> {
                    assertThat(d)
                            .usingRecursiveComparison()
                            .isEqualTo(driver);
                });
    }

    @Test
    void itShouldSelectDriverByEmail() throws ParseException {
        // Given...Driver information
        String email = "keitamohamed12@gmail.com";
        Driver driver = new Driver(64726181L, "Mohamed", "Keita", "keitamohamed12@gmail.com", "5405666378");
        driver.setDob(format.parse(dob));
        driver.setGender("Male");
        // When
        underTest.save(driver);
        // Then
        Optional<Driver> optionalDriver = underTest.findDriverByEmailAddress(email);
        assertThat(optionalDriver)
                .isPresent()
                .hasValueSatisfying( d -> {
                    assertThat(d)
                            .usingRecursiveComparison()
                            .isEqualTo(driver)
                    ;
                });
    }

    @Test
    void itShouldNotSelectDriverWhenEmailDoesNotExists() {
        // Given...Driver information
        String email = "keitamohamed12@gmail.com";
        // When
        Optional<Driver> optionalDriver = underTest.findDriverByEmailAddress(email);
        // Then
        assertThat(optionalDriver).isNotPresent();
    }

    @Test
    void itShouldSaveDriver() throws ParseException {
        // Given...Driver information
        long id = 7823516L;
        Driver driver = new Driver(id, "Mohamed", "Keita", "keitamohamed12@gmail.com", "5405666378");
        driver.setDob(format.parse(dob));
        driver.setGender("Male");

        // When
        underTest.save(driver);
        // Then
        Optional<Driver> optionalDriver = underTest.findById(id);
        assertThat(optionalDriver)
                .isPresent()
                .hasValueSatisfying(d -> {
                    assertThat(d)
                            .usingRecursiveComparison()
                            .isEqualTo(driver);
                });
    }

    @Test
    void itShouldNotSaveDriverWhenNameIsNull() throws ParseException {
        // Given...Driver information
        Driver driver = new Driver(737267181L, "Mohamed", "Keita", "keitamohamed12@gmail.com", "5405666378");
        driver.setDob(format.parse(dob));
        driver.setGender("Male");
        DriverRegistration registration = new DriverRegistration(driver);
        // When
        // Then
        assertThatThrownBy(() -> underTest.save(registration.getDriver()))
                .hasMessageContaining("not-null property references a null or transient value :")
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}