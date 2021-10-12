package com.ebki.service;

import com.ebki.model.Address;
import com.ebki.repository.DriverRepo;
import com.ebki.model.Driver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

class DriverServiceTest {

    @Mock
    private DriverRepo driverRepositoryUnderTest;

    @Captor
    private ArgumentCaptor<Driver> driverArgumentCaptor;

    private DriverService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new DriverService(driverRepositoryUnderTest);
    }

    @Test
    void itShouldSaveNewDriver() {
        // Given
        Set<Address> addressList = new HashSet<>();
        Driver driver = new Driver(7823516L, "Mohamed", "Keita", "keitamohamed12@gmail.com", "5405666378");
        addressList.add(new Address(6252622L, "5627 City Street", "Phoenix", "AZ", 26152, driver));
        driver.setAddress(addressList);

        given(driverRepositoryUnderTest.findDriverByEmailAddress(driver.getEmail()))
                .willReturn(Optional.empty());

        // When
        underTest.registerNewDriver(driver);

        //then
        then(driverRepositoryUnderTest).should().save(driverArgumentCaptor.capture());
        Driver driverCaptorValue = driverArgumentCaptor.getValue();
        assertThat(driverCaptorValue).isEqualTo(driver);
    }

    @Test
    void itShouldSaveNewDriverWhenIdIsNull() {
        // Given
        Set<Address> addressList = new HashSet<>();
        Driver driver = new Driver(null, "Mohamed", "Keita", "keitamohamed12@gmail.com", "5405666378");
        addressList.add(new Address(6252622L, "5627 City Street", "Phoenix", "AZ", 26152, driver));
        driver.setAddress(addressList);
        given(driverRepositoryUnderTest.findDriverByEmailAddress(driver.getEmail()))
                .willReturn(Optional.empty());
        // When
        underTest.registerNewDriver(driver);

        //then
        then(driverRepositoryUnderTest).should().save(driverArgumentCaptor.capture());
        Driver driverCaptorValue = driverArgumentCaptor.getValue();
        // assertThat(driverCaptorValue).isEqualToIgnoringGivenFields(driver, "driverID");
        assertThat(driverCaptorValue)
                .usingRecursiveComparison()
                .ignoringFields("name", "driverID")
                .isEqualTo(driver);
        assertThat(driverCaptorValue.getDriverID()).isNotNull();
    }

    @Test
    void itShouldNotSaveDriverWhenDriverExists() {
        // Given
        long id = 7823516L;
        Driver driver = new Driver(id, "Mohamed", "Keita", "keitamohamed12@gmail.com", "5405666378");

        // ... an existing driver is return
        given(driverRepositoryUnderTest.findDriverByEmailAddress(driver.getEmail()))
                .willReturn(Optional.of(driver));
        // When
        underTest.registerNewDriver(driver);

        //then
        then(driverRepositoryUnderTest).should(never()).save(any());
    }

    @Test
    void itShouldThrowWhenEmailIsTaken() {
        // Given a driver information
        long id = 7823516L;
        Driver driver = new Driver(id, "Mohamed", "Keita", "keitamohamed12@gmail.com", "5405666378");
        Driver driverTwo = new Driver(id, "Fayanga", "Fayanga", "keitamohamed12@gmail.com", "5405666378");

        // ... No customer with am email address
        given(driverRepositoryUnderTest.findDriverByEmailAddress(driver.getEmail()))
                .willReturn(Optional.of(driverTwo));
        // When
        // Then
        assertThatThrownBy(() -> underTest.registerNewDriver(driver))
                .isInstanceOf(IllegalStateException.class)
                        .hasMessageContaining(String.format("Email address [%s] is taken", driver.getEmail()));

        // Finally
        then(driverRepositoryUnderTest).should(never()).save(any(Driver.class));
    }

}