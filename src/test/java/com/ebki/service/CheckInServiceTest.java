package com.ebki.service;

import com.ebki.model.Car;
import com.ebki.model.CarCheckIn;
import com.ebki.repository.CheckInRepo;
import com.ebki.request.CheckIn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;


class CheckInServiceTest {

    @Mock
    private CheckInRepo repo;
    private CheckInService service;
    @Captor
    private ArgumentCaptor<CarCheckIn> argumentCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new CheckInService(repo);
    }

    @Test
    void itShouldCheckInCar() {
        // Given
        CarCheckIn checkIn = new CarCheckIn();
        Car car = new Car(536118L, "Toyota", "RAV4", "SUV", 2021);

        checkIn.setCheckInID(532188367L);
        checkIn.setCar(car);

        // Given that Checkin ID, It should return empty optional
        given(repo.findById(checkIn.getCheckInID()))
                .willReturn(Optional.empty());

        // When ... It should save the checkin car
        service.save(checkIn);

        // Then ... it should capture the CHECK_IN_CAR And RETURN the CHECK_IN_CAR
        then(repo).should().save(argumentCaptor.capture());
        CarCheckIn capture = argumentCaptor.getValue();
        assertThat(capture).isSameAs(checkIn);
    }

    @Test
    void itShouldFindCarCheckInByCarBrandAndModel() {
        // Given
        CarCheckIn check = new CarCheckIn();
        CarCheckIn checkTwo = new CarCheckIn();

        check.setCheckInID(53778288L);
        check.setCar(new Car(836622L, "Nissan", "37Z", "Open Top", 2004));

        checkTwo.setCheckInID(35611821L);
        checkTwo.setCar(new Car(89973632L, "Nissan", "Maxima", "Close Top", 2020));

        // When ... It should save the two new CHECK_IN CAR
        service.save(check);
        service.save(checkTwo);

        // Then
        //...Verify that two CHECK_IN CAR are being CHECK_IN, Capture it
        verify(repo, times(2)).save(argumentCaptor.capture());
        //...GET all the CHECK_IN CAR
        List<CarCheckIn> capture = argumentCaptor.getAllValues();
        assertThat(service.getRepo(capture, check.getCar().getBrand(), check.getCar().getModel()))
                .asList().doesNotContain(checkTwo);
    }

    @Test
    void itShouldThrowExceptionWhenCarExist() {
        // Given
        CarCheckIn check = new CarCheckIn();
        CarCheckIn checkTwo = new CarCheckIn();

        check.setCheckInID(35611821L);
        check.setCar(new Car(836622L, "Nissan", "Maxima", "Open Top", 2004));

        checkTwo.setCheckInID(35611821L);
        checkTwo.setCar(new Car(89973632L, "Nissan", "37Z", "Open Top", 2004));

        given(repo.findById(check.getCheckInID()))
                .willReturn(Optional.of(checkTwo));

        // When
        // Then
        assertThatThrownBy(() -> service.save(check))
                .hasMessageContaining(String.format(
                        "CheckIn with ID [%s] already exists",
                        check.getCheckInID()))
                .isInstanceOf(IllegalStateException.class);
        then(repo).should(never()).save(any(CarCheckIn.class));
    }


}