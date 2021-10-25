package com.ebki.service;

import com.ebki.model.Address;
import com.ebki.model.Driver;
import com.ebki.repository.DriverRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
        // Given...THE NEW DRIVER INFORMATION AND ADDRESS
        Set<Address> addressList = new HashSet<>();
        Driver driver = new Driver(7823516L, "John", "Robert", "roberts@gmail.com", "5405666378");
        addressList.add(new Address(6252622L, "5627 City Street", "Phoenix", "AZ", 26152, driver));
        driver.setAddress(addressList);

        //...IT SHOULD RETURN AN EMPTY OPTIONAL OF A DRIVER SINCE THE EMAIL DOES NOT EXIST
        given(driverRepositoryUnderTest.findDriverByEmailAddress(driver.getEmail()))
                .willReturn(Optional.empty());

        // When...IT SHOULD SAVE THE REGISTRATION REQUEST
        underTest.save(driver);

        //then...IT SHOULD SAVE THE REQUEST AND CAPTURE THE VALUE
        then(driverRepositoryUnderTest).should().save(driverArgumentCaptor.capture());
        //...IT SHOULD RETURN THE CAPTURE VALUE
        Driver driverCaptorValue = driverArgumentCaptor.getValue();
        //...IT SHOULD CHECK THE CAPTURE VALUE IS EQUAL TO THE ORIGINAL REQUEST THAT WAS SENT
        assertThat(driverCaptorValue).isEqualTo(driver);
    }

    @Test
    void itShouldSaveNewDriverWhenIdIsNull() {
        // Given...THE NEW DRIVER INFORMATION AND ADDRESS
        Set<Address> addressList = new HashSet<>();
        Driver driver = new Driver(null, "John", "Robert", "roberts@gmail.com", "5405666378");
        addressList.add(new Address(6252622L, "5627 City Street", "Phoenix", "AZ", 26152, driver));
        driver.setAddress(addressList);
        given(driverRepositoryUnderTest.findDriverByEmailAddress(driver.getEmail()))
                .willReturn(Optional.empty());

        // When...IT SHOULD SAVE THE DRIVER REGISTRATION REQUEST
        underTest.save(driver);

        //then...IT SHOULD SAVE THE REQUEST AND CAPTURE THE REQUEST
        then(driverRepositoryUnderTest).should().save(driverArgumentCaptor.capture());
        //...IT SHOULD RETURN THE CAPTURE VALUE
        Driver driverCaptorValue = driverArgumentCaptor.getValue();
        //...IT SHOULD CHECK COMPARE THE CAPTURE VALUE WITH THE ORIGINAL REQUEST THAT WAS SENT
        assertThat(driverCaptorValue)
                .usingRecursiveComparison()
                .ignoringFields("name", "driverID")
                .isEqualTo(driver);
        //...IT SHOULD CHECK THE CAPTURE DRIVER ID IS NOT NULL
        assertThat(driverCaptorValue.getDriverID()).isNotNull();
    }

    @Test
    void itShouldNotSaveDriverWhenDriverExists() {
        // Given
        long id = 7823516L;
        Driver driver = new Driver(id, "John", "Robert", "roberts@gmail.com", "5405666378");

        // ... IT SHOULD RETURN AN EXISTING DRIVER
        given(driverRepositoryUnderTest.findDriverByEmailAddress(driver.getEmail()))
                .willReturn(Optional.of(driver));
        // When...IT SHOULD NOT REGISTER NEW DRIVER
        underTest.save(driver);

        //then...IT SHOULD NOT SAVE ANY DRIVER REGISTRATION REQUEST
        then(driverRepositoryUnderTest).should(never()).save(any());
    }

    @Test
    void itShouldThrownExceptionWhenEmailExists() {
        // Given...DRIVER NEW INFORMATION
        long id = 7823516L;
        Driver driver = new Driver(id, "John", "Robert", "roberts@gmail.com", "5405666378");
        Driver driverTwo = new Driver(id, "Fayanga", "Fayanga", "keitamohamed12@gmail.com", "5405666378");

        // ...IT SHOULD RETURN AN EXISTING DRIVER WITH GIVEN EMAIL
        given(driverRepositoryUnderTest.findDriverByEmailAddress(driver.getEmail()))
                .willReturn(Optional.of(driverTwo));
        // When
        // Then...EMAIL ADDRESS EXISTS, IT SHOULD THROW ILLEGAL STATE EXCEPTION
        assertThatThrownBy(() -> underTest.save(driver))
                .isInstanceOf(IllegalStateException.class)
                        .hasMessageContaining(String.format("Email address [%s] is taken", driver.getEmail()));

        // Finally...IT SHOULD NOT SAY THE REQUEST SINCE THE ALREADY EMAIL EXISTS
        then(driverRepositoryUnderTest).should(never()).save(any(Driver.class));
    }

    @Test
    void itShouldThrowExceptionWhenDriverIDDoesNotExist() {
        // Given
        long driverID = 0L;
        // When
        // Then
        assertThatThrownBy(() -> underTest.deleteDriver(driverID))
                .hasMessageContaining(String.format("[ %s ] is an invalid driver id.", driverID))
                .isInstanceOf(IllegalStateException.class);

    }
}