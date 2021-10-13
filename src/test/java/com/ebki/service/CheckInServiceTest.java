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

        // Given that Checkin ID, It should return empty optional
        given(repo.findById(checkIn.getCheckInID()))
                .willReturn(Optional.empty());

        // When ... It should save the checkin car
        service.save(checkIn);

        // Then ... it should capture the CHECK_OUT_CAR And RETURN the CHECK_OUT_CAR
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

        // When ... It should save the two new CHECK_OUT CAR
        service.save(firstCheckIn);
        service.save(secondCheckIn);

        // Then
        //...It should Verify there are two CHECK_OUT CAR being CHECK_OUT, And it should Capture the CHECK_OUT
        verify(repo, times(2)).save(argumentCaptor.capture());
        //...GET all the CHECK_OUT CAR
        List<CarCheckIn> capture = argumentCaptor.getAllValues();
        //...It should return list of CHECK_OUT Cars, CHECK the list contain firstCheckIn
        // and DOES NOT CONTAIN secondCheckIn
        assertThat(service.findCheckInByCarBrandAndModel(capture, firstCheckIn.getCar().getBrand(), firstCheckIn.getCar().getModel()))
                .asList()
                .contains(firstCheckIn)
                .doesNotContain(secondCheckIn);
    }

    @Test
    void itShouldThrowExceptionWhenListIsEmpty() {
        // Given
        CarCheckIn firstCheckIn = new CarCheckIn();
        List<CarCheckIn> list = new ArrayList<>();

        firstCheckIn.setCheckInID(53778288L);
        firstCheckIn.setCar(new Car(836622L, "Nissan", "37Z", "Open Top", 2004));
        // When

        // Then
        //...it should throw exception, List is empty
        assertThatThrownBy(() -> service.findCheckInByCarBrandAndModel(list, firstCheckIn.getCar().getBrand(), firstCheckIn.getCar().getModel()))
                .hasMessageContaining("No car in the checkin database")
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void itShouldFindCheckInByCarBrand() {
        // Given
        List<Car> carList = fakeData.carList();
        List<CarCheckIn> carCheckInList = fakeData.carCheckInList();

        int[] index = {0};
        carCheckInList.forEach(checkIn -> checkIn.setCar(carList.get(index[0]++)));

        // When ... It should save the two new CHECK_OUT CAR
        carCheckInList.forEach(carCheckIn -> service.save(carCheckIn));

        // Then
        //...It should Verify there are two CHECK_OUT CAR being CHECK_OUT, And it should Capture the CHECK_OUT
        verify(repo, times(carList.size())).save(argumentCaptor.capture());
        //...GET all the CHECK_OUT CAR
        List<CarCheckIn> capture = argumentCaptor.getAllValues();
        //...It should return list of CHECK_OUT Cars, CHECK the list contain lastCheckIn
        // and DOES NOT CONTAIN firstCheckIn
        assertThat(service.findCheckInByCarBrand(capture, "Toyota"))
                .asList()
                .contains(carCheckInList.get(carList.size() - 1))
                .doesNotContain(carCheckInList.get(1))
                .hasSize(2);
    }

    @Test
    void itShouldThrowExceptionWhenListIsEmptyForFindCarByCarBrand() {
        // Given
        CarCheckIn firstCheckIn = new CarCheckIn();
        List<CarCheckIn> list = new ArrayList<>();

        firstCheckIn.setCheckInID(53778288L);
        firstCheckIn.setCar(new Car(836622L, "Nissan", "37Z", "Open Top", 2004));
        // When

        // Then
        //...it should throw exception, List is empty
        assertThatThrownBy(() -> service.findCheckInByCarBrand(list, firstCheckIn.getCar().getBrand()))
                .hasMessageContaining("No car in the checkin database")
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void itShouldThrowExceptionWhenCarBrandIsEmpty() {
        // Given
        CarCheckIn firstCheckIn = new CarCheckIn();
        List<CarCheckIn> list = new ArrayList<>();

        firstCheckIn.setCheckInID(53778288L);
        firstCheckIn.setCar(new Car(836622L, "", "37Z", "Open Top", 2004));

        // When
        list.add(firstCheckIn);

        // Then
        //...it should throw exception, List is empty
        assertThatThrownBy(() -> service.findCheckInByCarBrand(list, firstCheckIn.getCar().getBrand()))
                .hasMessageContaining(String.format("Value for car brand is [%s] ", firstCheckIn.getCar().getBrand()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void itShouldFindCheckInByCarYear() {
        // Given
        List<Car> carList = fakeData.carList();
        List<CarCheckIn> carCheckInList = fakeData.carCheckInList();

        int[] index = {0};
        carCheckInList.forEach(checkIn -> checkIn.setCar(carList.get(index[0]++)));

        // When ... It should save the two new CHECK_OUT CAR
        carCheckInList.forEach(carCheckIn -> service.save(carCheckIn));

        // Then
        //...It should Verify there are two CHECK_OUT CAR being CHECK_OUT, And it should Capture the CHECK_OUT
        verify(repo, times(carList.size())).save(argumentCaptor.capture());
        //...GET all the CHECK_OUT CAR
        List<CarCheckIn> capture = argumentCaptor.getAllValues();
        //...It should return list of CHECK_OUT Cars, CHECK the list contain lastCheckIn
        // and DOES NOT CONTAIN secondCheckIn
        assertThat(service.findCheckInByCarYear(capture, 2007))
                .asList()
                .contains(carCheckInList.get(carList.size() - 1))
                .doesNotContain(carCheckInList.get(2))
                .hasSize(2);
    }

    @Test
    void itShouldThrowExceptionForFindCheckInByCarYearWhenListIsEmpty() {
        // Given
        CarCheckIn firstCheckIn = new CarCheckIn();
        List<CarCheckIn> list = new ArrayList<>();

        firstCheckIn.setCheckInID(53778288L);
        firstCheckIn.setCar(new Car(836622L, "Nissan", "37Z", "Open Top", 2004));
        // When

        // Then
        //...it should throw exception, List is empty
        assertThatThrownBy(() -> service.findCheckInByCarYear(list, firstCheckIn.getCar().getYear()))
                .hasMessageContaining("No car in the checkin database")
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void itShouldThrowExceptionForFindCheckInByCarBrandWhenYearIsEmpty() {
        // Given
        CarCheckIn firstCheckIn = new CarCheckIn();
        List<CarCheckIn> list = new ArrayList<>();

        firstCheckIn.setCheckInID(53778288L);
        firstCheckIn.setCar(new Car(836622L, "Nissan", "37Z", "Open Top", 0));

        // When
        list.add(firstCheckIn);

        // Then
        //...it should throw exception, List is empty
        assertThatThrownBy(() -> service.findCheckInByCarYear(list, firstCheckIn.getCar().getYear()))
                .hasMessageContaining(String.format("Value for car year is [%s] ", firstCheckIn.getCar().getYear()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void itShouldThrowExceptionWhenCarBrandOrModelIsNull() {
        // Given
        CarCheckIn firstCheckIn = new CarCheckIn();
        CarCheckIn secondCheckIn = new CarCheckIn();

        firstCheckIn.setCheckInID(53778288L);
        firstCheckIn.setCar(new Car(836622L, "Nissan", "", "Open Top", 2004));

        secondCheckIn.setCheckInID(35611821L);
        secondCheckIn.setCar(new Car(89973632L, "Nissan", "Maxima", "Close Top", 2020));

        // When ... It should save the two new CHECK_OUT CAR
        service.save(firstCheckIn);
        service.save(secondCheckIn);

        // Then
        //...It should Verify there are two CHECK_OUT CAR being CHECK_OUT, And it should Capture the CHECK_OUT
        verify(repo, times(2)).save(argumentCaptor.capture());
        //...GET all the CHECK_OUT CAR
        List<CarCheckIn> capture = argumentCaptor.getAllValues();
        //...it should throw IllegalArgumentException, Car model is empty
        assertThatThrownBy(() -> service.findCheckInByCarBrandAndModel(capture, firstCheckIn.getCar().getBrand(), firstCheckIn.getCar().getModel()))
                .hasMessageContaining(String.format("Value for car brand is [%s] and model is [%s]",
                        firstCheckIn.getCar().getBrand(), firstCheckIn.getCar().getModel()))
                .isInstanceOf(IllegalArgumentException.class);
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

        //... Will return CHECK_OUT CAR With SAME ID as secondCheckIn
        given(repo.findById(firstCheckIN.getCheckInID()))
                .willReturn(Optional.of(secondCheckIN));

        // When
        // Then ...it Should throw IllegalStateException, Since there is CHECK_OUT CAR
        // with same CHECK_OUT ID number
        assertThatThrownBy(() -> service.save(firstCheckIN))
                .hasMessageContaining(String.format(
                        "CheckIn with ID [%s] already exists",
                        firstCheckIN.getCheckInID()))
                .isInstanceOf(IllegalStateException.class);
        //... It should not save CHECK_OUT Car
        then(repo).should(never()).save(any(CarCheckIn.class));
    }


}