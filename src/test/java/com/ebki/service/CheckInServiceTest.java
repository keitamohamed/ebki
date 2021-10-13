package com.ebki.service;

import com.ebki.FakeData;
import com.ebki.model.Car;
import com.ebki.model.CarCheckIn;
import com.ebki.repository.CheckInRepo;
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


class CheckInServiceTest {

    @Mock
    private CheckInRepo repo;
    private CheckInService service;
    @Captor
    private ArgumentCaptor<CarCheckIn> argumentCaptor;


    private final FakeData fakeData = new FakeData();

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

        // GIVEN THAT CHECKIN ID, IT SHOULD RETURN EMPTY OPTIONAL
        given(repo.findById(checkIn.getCheckInID()))
                .willReturn(Optional.empty());

        // WHEN ... IT SHOULD SAVE THE CHECKIN CAR
        service.save(checkIn);

        // THEN ... IT SHOULD CAPTURE THE CHECK_IN AND RETURN THE CHECK_IN_CAR
        then(repo).should().save(argumentCaptor.capture());
        CarCheckIn capture = argumentCaptor.getValue();
        assertThat(capture).isSameAs(checkIn);
    }

    @Test
    void itShouldFindCarCheckInByCarBrandAndModel() {
        // GIVEN
        CarCheckIn firstCheckIn = new CarCheckIn();
        CarCheckIn secondCheckIn = new CarCheckIn();

        firstCheckIn.setCheckInID(53778288L);
        firstCheckIn.setCar(new Car(836622L, "Nissan", "37Z", "Open Top", 2004));

        secondCheckIn.setCheckInID(35611821L);
        secondCheckIn.setCar(new Car(89973632L, "Nissan", "Maxima", "Close Top", 2020));

        // WHEN ... IT SHOULD SAVE THE TWO NEW CHECK_IN CAR
        service.save(firstCheckIn);
        service.save(secondCheckIn);

        // THEN
        //...IT SHOULD VERIFY THERE ARE TWO CHECK_IN CAR BEING CHECK_IN, AND IT SHOULD CAPTURE THE CHECK_IN
        verify(repo, times(2)).save(argumentCaptor.capture());
        //...GET ALL THE CHECK_IN CAR
        List<CarCheckIn> capture = argumentCaptor.getAllValues();
        //...IT SHOULD RETURN LIST OF CHECK_IN CARS, CHECK THE LIST CONTAIN FIRST_CHECKIN
        // AND DOES NOT CONTAIN SECOND_CHECKIN
        assertThat(service.findCheckInByCarBrandAndModel(capture, firstCheckIn.getCar().getBrand(), firstCheckIn.getCar().getModel()))
                .asList()
                .contains(firstCheckIn)
                .doesNotContain(secondCheckIn);
    }

    @Test
    void itShouldThrowExceptionWhenListIsEmpty() {
        // GIVEN
        CarCheckIn firstCheckIn = new CarCheckIn();
        List<CarCheckIn> list = new ArrayList<>();

        firstCheckIn.setCheckInID(53778288L);
        firstCheckIn.setCar(new Car(836622L, "Nissan", "37Z", "Open Top", 2004));

        // WHEN
        // THEN
        //...IT SHOULD THROW EXCEPTION, LIST IS EMPTY
        assertThatThrownBy(() -> service.findCheckInByCarBrandAndModel(list, firstCheckIn.getCar().getBrand(), firstCheckIn.getCar().getModel()))
                .hasMessageContaining("No car in the checkin database")
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void itShouldFindCheckInByCarBrand() {
        // GIVEN
        List<Car> carList = fakeData.carList();
        List<CarCheckIn> carCheckInList = fakeData.carCheckInList();

        int[] index = {0};
        carCheckInList.forEach(checkIn -> checkIn.setCar(carList.get(index[0]++)));

        // WHEN ... IT SHOULD SAVE THE TWO NEW CHECK_IN CAR
        carCheckInList.forEach(carCheckIn -> service.save(carCheckIn));

        // THEN
        //...IT SHOULD VERIFY THERE ARE TWO CHECK_IN CAR BEING CHECK_IN, AND IT SHOULD CAPTURE THE CHECK_IN
        verify(repo, times(carList.size())).save(argumentCaptor.capture());
        //...GET ALL THE CHECK_IN CAR
        List<CarCheckIn> capture = argumentCaptor.getAllValues();
        //...IT SHOULD RETURN LIST OF CHECK_IN CARS, CHECK THE LIST CONTAIN LAST_CHECKIN
        // AND DOES NOT CONTAIN FIRST_CHECKIN
        assertThat(service.findCheckInByCarBrand(capture, "Toyota"))
                .asList()
                .contains(carCheckInList.get(carList.size() - 1))
                .doesNotContain(carCheckInList.get(1))
                .hasSize(2);
    }

    @Test
    void itShouldThrowExceptionWhenListIsEmptyForFindCarByCarBrand() {
        // GIVEN
        CarCheckIn firstCheckIn = new CarCheckIn();
        List<CarCheckIn> list = new ArrayList<>();

        firstCheckIn.setCheckInID(53778288L);
        firstCheckIn.setCar(new Car(836622L, "Nissan", "37Z", "Open Top", 2004));

        // WHEN
        // THEN
        //...IT SHOULD THROW EXCEPTION, LIST IS EMPTY
        assertThatThrownBy(() -> service.findCheckInByCarBrand(list, firstCheckIn.getCar().getBrand()))
                .hasMessageContaining("No car in the checkin database")
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void itShouldThrowExceptionWhenCarBrandIsEmpty() {
        // GIVEN
        CarCheckIn firstCheckIn = new CarCheckIn();
        List<CarCheckIn> list = new ArrayList<>();

        firstCheckIn.setCheckInID(53778288L);
        firstCheckIn.setCar(new Car(836622L, "", "37Z", "Open Top", 2004));

        // WHEN
        list.add(firstCheckIn);

        // THEN
        //...IT SHOULD THROW EXCEPTION, LIST IS EMPTY
        assertThatThrownBy(() -> service.findCheckInByCarBrand(list, firstCheckIn.getCar().getBrand()))
                .hasMessageContaining(String.format("Value for car brand is [%s] ", firstCheckIn.getCar().getBrand()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void itShouldFindCheckInByCarYear() {
        // GIVEN
        List<Car> carList = fakeData.carList();
        List<CarCheckIn> carCheckInList = fakeData.carCheckInList();

        int[] index = {0};
        carCheckInList.forEach(checkIn -> checkIn.setCar(carList.get(index[0]++)));

        // WHEN ... IT SHOULD SAVE THE TWO NEW CHECK_IN CAR
        carCheckInList.forEach(carCheckIn -> service.save(carCheckIn));

        // THEN
        //...IT SHOULD VERIFY THERE ARE TWO CHECK_IN CAR BEING CHECK_IN, AND IT SHOULD CAPTURE THE CHECK_IN
        verify(repo, times(carList.size())).save(argumentCaptor.capture());
        //...GET ALL THE CHECK_IN CAR
        List<CarCheckIn> capture = argumentCaptor.getAllValues();
        //...IT SHOULD RETURN LIST OF CHECK_IN CARS, CHECK THE LIST CONTAIN LAST_CHECKIN
        // AND DOES NOT CONTAIN SECOND_CHECKIN
        assertThat(service.findCheckInByCarYear(capture, 2007))
                .asList()
                .contains(carCheckInList.get(carList.size() - 1))
                .doesNotContain(carCheckInList.get(2))
                .hasSize(2);
    }

    @Test
    void itShouldThrowExceptionForFindCheckInByCarYearWhenListIsEmpty() {
        // GIVEN
        CarCheckIn firstCheckIn = new CarCheckIn();
        List<CarCheckIn> list = new ArrayList<>();

        firstCheckIn.setCheckInID(53778288L);
        firstCheckIn.setCar(new Car(836622L, "Nissan", "37Z", "Open Top", 2004));

        // WHEN
        // THEN
        //...IT SHOULD THROW EXCEPTION, LIST IS EMPTY
        assertThatThrownBy(() -> service.findCheckInByCarYear(list, firstCheckIn.getCar().getYear()))
                .hasMessageContaining("No car in the checkin database")
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void itShouldThrowExceptionForFindCheckInByCarBrandWhenYearIsEmpty() {
        // GIVEN
        CarCheckIn firstCheckIn = new CarCheckIn();
        List<CarCheckIn> list = new ArrayList<>();

        firstCheckIn.setCheckInID(53778288L);
        firstCheckIn.setCar(new Car(836622L, "Nissan", "37Z", "Open Top", 0));

        // WHEN
        list.add(firstCheckIn);

        // THEN
        //...IT SHOULD THROW EXCEPTION, LIST IS EMPTY
        assertThatThrownBy(() -> service.findCheckInByCarYear(list, firstCheckIn.getCar().getYear()))
                .hasMessageContaining(String.format("Value for car year is [%s] ", firstCheckIn.getCar().getYear()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void itShouldThrowExceptionWhenCarBrandOrModelIsNull() {
        // GIVEN
        CarCheckIn firstCheckIn = new CarCheckIn();
        CarCheckIn secondCheckIn = new CarCheckIn();

        firstCheckIn.setCheckInID(53778288L);
        firstCheckIn.setCar(new Car(836622L, "Nissan", "", "Open Top", 2004));

        secondCheckIn.setCheckInID(35611821L);
        secondCheckIn.setCar(new Car(89973632L, "Nissan", "Maxima", "Close Top", 2020));

        // WHEN ... IT SHOULD SAVE THE TWO NEW CHECK_IN CAR
        service.save(firstCheckIn);
        service.save(secondCheckIn);

        // THEN
        //...IT SHOULD VERIFY THERE ARE TWO CHECK_IN CAR BEING CHECK_IN, AND IT SHOULD CAPTURE THE CHECK_IN
        verify(repo, times(2)).save(argumentCaptor.capture());
        //...GET ALL THE CHECK_OUT CAR
        List<CarCheckIn> capture = argumentCaptor.getAllValues();
        //...IT SHOULD THROW ILLEGAL ARGUMENT EXCEPTION, CAR MODEL IS EMPTY
        assertThatThrownBy(() -> service.findCheckInByCarBrandAndModel(capture, firstCheckIn.getCar().getBrand(), firstCheckIn.getCar().getModel()))
                .hasMessageContaining(String.format("Value for car brand is [%s] and model is [%s]",
                        firstCheckIn.getCar().getBrand(), firstCheckIn.getCar().getModel()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void itShouldThrowExceptionWhenCarExist() {
        // GIVEN
        CarCheckIn firstCheckIN = new CarCheckIn();
        CarCheckIn secondCheckIN = new CarCheckIn();

        firstCheckIN.setCheckInID(35611821L);
        firstCheckIN.setCar(new Car(836622L, "Nissan", "Maxima", "Open Top", 2004));

        secondCheckIN.setCheckInID(35611821L);
        secondCheckIN.setCar(new Car(89973632L, "Nissan", "37Z", "Open Top", 2004));

        //... WILL RETURN CHECK_IN CAR WITH SAME ID AS SECOND_CHECKIN
        given(repo.findById(firstCheckIN.getCheckInID()))
                .willReturn(Optional.of(secondCheckIN));

        // WHEN
        // THEN ...IT SHOULD THROW ILLEGAL STATE EXCEPTION, SINCE THERE IS CHECK_IN CAR
        // WITH SAME CHECK_IN ID NUMBER
        assertThatThrownBy(() -> service.save(firstCheckIN))
                .hasMessageContaining(String.format(
                        "CheckIn with ID [%s] already exists",
                        firstCheckIN.getCheckInID()))
                .isInstanceOf(IllegalStateException.class);
        //... IT SHOULD NOT SAVE CHECK_IN CAR
        then(repo).should(never()).save(any(CarCheckIn.class));
    }
}