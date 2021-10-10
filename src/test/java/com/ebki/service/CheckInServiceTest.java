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
        CarCheckIn firstCheckIn = new CarCheckIn();
        CarCheckIn secondCheckIn = new CarCheckIn();

        firstCheckIn.setCheckInID(53778288L);
        firstCheckIn.setCar(new Car(836622L, "Nissan", "37Z", "Open Top", 2004));

        secondCheckIn.setCheckInID(35611821L);
        secondCheckIn.setCar(new Car(89973632L, "Nissan", "Maxima", "Close Top", 2020));

        // When ... It should save the two new CHECK_IN CAR
        service.save(firstCheckIn);
        service.save(secondCheckIn);

        // Then
        //...It should Verify there are two CHECK_IN CAR being CHECK_IN, And it should Capture the CHECK_IN
        verify(repo, times(2)).save(argumentCaptor.capture());
        //...GET all the CHECK_IN CAR
        List<CarCheckIn> capture = argumentCaptor.getAllValues();
        //...It should return list of CHECK_IN Cars, CHECK the list contain firstCheckIn
        // and DOES NOT CONTAIN secondCheckIn
        assertThat(service.getRepo(capture, firstCheckIn.getCar().getBrand(), firstCheckIn.getCar().getModel()))
                .asList()
                .contains(firstCheckIn)
                .doesNotContain(secondCheckIn);
    }

    @Test
    void itShouldThrowExceptionWhenCarExist() {
        // Given
        CarCheckIn firstCheckIN = new CarCheckIn();
        CarCheckIn secondCheckIN = new CarCheckIn();

        firstCheckIN.setCheckInID(35611821L);
        firstCheckIN.setCar(new Car(836622L, "Nissan", "Maxima", "Open Top", 2004));

        secondCheckIN.setCheckInID(35611821L);
        secondCheckIN.setCar(new Car(89973632L, "Nissan", "37Z", "Open Top", 2004));

        //... Will return CHECK_IN CAR With SAME ID as secondCheckIn
        given(repo.findById(firstCheckIN.getCheckInID()))
                .willReturn(Optional.of(secondCheckIN));

        // When
        // Then ...it Should throw IllegalStateException, Since there is CHECK_IN CAR
        // with same CHECK_IN ID number
        assertThatThrownBy(() -> service.save(firstCheckIN))
                .hasMessageContaining(String.format(
                        "CheckIn with ID [%s] already exists",
                        firstCheckIN.getCheckInID()))
                .isInstanceOf(IllegalStateException.class);
        //... It should not save CHECK_IN Car
        then(repo).should(never()).save(any(CarCheckIn.class));
    }


}