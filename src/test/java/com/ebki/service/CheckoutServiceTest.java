//package com.ebki.service;
//
//import com.ebki.TestData;
//import com.ebki.model.Car;
//import com.ebki.model.CarCheckIn;
//import com.ebki.model.CarCheckout;
//import com.ebki.model.Driver;
//import com.ebki.repository.CheckOutRepo;
//import com.ebki.request.CheckOut;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Captor;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.BDDMockito.then;
//import static org.mockito.Mockito.*;
//
//class CheckoutServiceTest {
//
//    @Mock
//    private CheckOutRepo repo;
//    private CheckoutService service;
//    @Captor
//    private ArgumentCaptor<CarCheckout> argumentCaptor;
//
//    private final TestData fakeData = new TestData();
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        service = new CheckoutService(repo);
//    }
//
//    @Test
//    void itShouldCheckOutCar() {
//        // Given...NEW CHECKOUT INFORMATION
//        long id = 672635511L;
//        Car car = new Car(id, "Nissan", "Maxima", "Open Top", 2004);
//        CarCheckout checkout = new CarCheckout();
//
//        checkout.setCheckoutID(6738161L);
//        checkout.setCarCheckOut(car);
//
//        // When...CHECKOUT ID, IT SHOULD RETURN EMPTY OPTIONAL
//        given(repo.findById(checkout.getCheckoutID()))
//                .willReturn(Optional.empty());
//
//        // Then...IT SHOULD SAVE CHECKOUT REQUEST
//        service.save(checkout, 0L, 0L);
//        //...IT SHOULD SAVE THE REQUEST AND CAPTURE IT
//        then(repo).should().save(argumentCaptor.capture());
//        //...IT SHOULD RETURN THE CAPTURE VALUE
//        CarCheckout capture = argumentCaptor.getValue();
//        //...IT SHOULD CHECK THAT THE CAPTURE VALUE IS EQUAL TO THE ORIGINAL REQUEST
//        assertThat(capture).isEqualTo(checkout);
//    }
//
//    @Test
//    void itShouldNotCheckOutCarWhenCarExists() {
//        // Given...NEW CHECKOUT INFORMATION
//        long id = 672635511L;
//        Car car = new Car(id, "Nissan", "Maxima", "Open Top", 2004);
//        CarCheckout checkout = new CarCheckout();
//
//        checkout.setCheckoutID(6728121L);
//        checkout.setCarCheckOut(car);
//
//        //...Given CHECKOUT ID, IT SHOULD RETURN EMPTY OPTIONAL
//        given(repo.findById(checkout.getCheckoutID()))
//                .willReturn(Optional.of(checkout));
//
//        // When...IT SHOULD NOT SAVE THE CHECKOUT REQUEST SINCE THE CHECKOUT ALREADY EXIST
//        service.save(checkout, 0L, 0L);
//        // Then...IT SHOULD CHECK THAT THE CHECKOUT REQUEST WAS NOT SAVE
//        then(repo).should(never()).save(any());
//    }
//
//    @Test
//    void itShouldThrowExceptionWhenCheckOutIDExists() {
//        // Given...NEW CHECKOUT INFORMATION
//        long id = 672635511L;
//        Car car = new Car(6271718L, "Nissan", "Maxima", "Open Top", 2004);
//        Car carTwo = new Car(6271718L, "Nissan", "37Z", "Open Top", 2004);
//
//        CarCheckout checkout = new CarCheckout();
//        CarCheckout checkoutTwo = new CarCheckout();
//
//        checkout.setCheckoutID(id);
//        checkoutTwo.setCheckoutID(id);
//        checkout.setCarCheckOut(car);
//        checkoutTwo.setCarCheckOut(carTwo);
//
//        //...IT SHOULD RETURN CHECKOUT WITH GIVEN ID THAT IS SAME AS CHECKOUT TWO
//        given(repo.findById(checkout.getCheckoutID()))
//                .willReturn(Optional.of(checkoutTwo));
//
//        // When
//        // Then ...IT SHOULD THROW ILLEGAL STATE EXCEPTION SINCE GIVEN ID ALREADY EXISTS
//        assertThatThrownBy(() -> service.save(checkout, 0L, 0L))
//                .isInstanceOf(IllegalStateException.class)
//                        .hasMessageContaining(String.format(
//                                "Car [ %s ] with vin number [ %s ] is already checkout ",
//                                car.getBrand(), car.getVin()));
//        //...IT SHOULD CHECK THAT THE REQUEST WAS NOT SAVE
//        then(repo).should(never()).save(any(CarCheckout.class));
//    }
//
//    @Test
//    void itShouldThrowExceptionWhenDriverDoesNotExists() {
//        // Given...CHECKOUT INFORMATION
//        CheckOut request = new CheckOut(new CarCheckout());
//        Optional<Driver> driver = Optional.empty();
//        Optional<Car> carOptional = Optional.empty();
//
//        // When
//        // Then...IT SHOULD THROW ILLEGAL STATE EXCEPTION
//        assertThatThrownBy(() ->
//                service.throwExceptionWhenDriverOrCarDoesNotExists(0L, 0L, driver, carOptional, request))
//                .hasMessageContaining(String.format("No driver found with an ID [ %s ]", 0L))
//                .isInstanceOf(IllegalStateException.class);
//    }
//
//    @Test
//    void itShouldThrowExceptionWhenOptionalCarDoesNotExist() {
//        // Given...CHECKOUT INFORMATION
//        CheckOut request = new CheckOut(new CarCheckout());
//        Optional<Driver> driver = Optional.of(new Driver(7466210L, "John", "Robert", "roberts@gmail.com", "5405666378"));
//        Optional<Car> carOptional = Optional.empty();
//
//        // When
//        // Then...IT SHOULD THROW ILLEGAL STATE EXCEPTION FOR NOT FINDING A CAR
//        assertThatThrownBy(() ->
//                service.throwExceptionWhenDriverOrCarDoesNotExists(0L, 0L, driver, carOptional, request))
//                .hasMessageContaining(String.format("No Car found with vin number [ %s ]", 0L))
//                .isInstanceOf(IllegalStateException.class);
//    }
//
//    @Test
//    void itShouldCheckOutCarWhenCheckOutIDIsNull() {
//        // Given...NEW CHECKOUT INFORMATION
//        long id = 672635511L;
//        Car car = new Car(id, "Nissan", "Maxima", "Open Top", 2004);
//
//        CarCheckout checkout = new CarCheckout();
//        checkout.setCarCheckOut(car);
//        checkout.setCheckoutID(null);
//
//        //...GIVEN THE CAR VIN NUMBER, IT SHOULD RETURN AN EMPTY OPTIONAL OF CHECKOUT
//        given(repo.findCarCheckoutByCar_CarVinNumber(car.getVin()))
//                .willReturn(Optional.empty());
//        // When...IT SHOULD SAVE THE CHECKOUT REQUEST
//        service.save(checkout, 0L, 0L);
//
//        // Then...IT SAVE THE REQUEST AND CAPTURE THE REQUEST
//        then(repo).should().save(argumentCaptor.capture());
//        //...IT SHOULD RETURN THE CAPTURE VALUE
//        CarCheckout captureValue = argumentCaptor.getValue();
//        assertThat(captureValue)
//                .usingRecursiveComparison()
//                .ignoringFields("name", "checkoutID")
//                .isEqualTo(checkout);
//        //...IT SHOULD CHECK THAT CAPTURE VALUE IS NOT NULL
//        assertThat(captureValue.getCheckoutID()).isNotNull();
//    }
//
//    @Test
//    void itShouldFindCheckOutByCarBrand() {
//        // Given...GIVEN CHECK_OUT INFORMATION
//        List<Car> carList = fakeData.carList();
//        List<CarCheckout> carCheckoutList = fakeData.carCheckoutList();
//
//        int[] index = {0};
//        carCheckoutList.forEach(checkout -> checkout.setCarCheckOut(carList.get(index[0]++)));
//
//        // When ...IT SHOULD SAVE THE CHECK_OUT LIST OF CARS
//        carCheckoutList.forEach(carCheckout -> service.save(carCheckout, 0L, 0L));
//
//        // Then
//        //...IT SHOULD VERIFY THE LIST CHECK_OUT CAR WAS SAVE, AND CAPTURE THE CHECK_OUT
//        verify(repo, times(carList.size())).save(argumentCaptor.capture());
//        //...GET ALL THE CHECK_OUT CAR
//        List<CarCheckout> capture = argumentCaptor.getAllValues();
//        //...IT SHOULD RETURN A LIST OF CHECK_OUT CARS, CHECK THE LIST CONTAIN THE SECOND_CHECKOUT
//        // AND DOES NOT CONTAIN THE FIRST_CHECKOUT AND THE LAST_CHECKOUT
//        assertThat(service.findCheckOutByCarBrand(capture, "Lexus"))
//                .asList()
//                .contains(carCheckoutList.get(1).getCarCheckOut())
//                .doesNotContain(
//                        carCheckoutList.get(0),
//                        carCheckoutList.get(carCheckoutList.size() - 1))
//                .hasSize(1);
//    }
//
//    @Test
//    void itShouldFindCheckOutByCarYear() {
//        // Given...CHECK_OUT INFORMATION
//        List<Car> carList = fakeData.carList();
//        List<CarCheckout> carCheckoutList = fakeData.carCheckoutList();
//
//        int[] index = {0};
//        carCheckoutList.forEach(checkout -> checkout.setCarCheckOut(carList.get(index[0]++)));
//
//        //When...IT SHOULD SAVE THE CHECK_OUT LIST OF CARS
//        carCheckoutList.forEach(carCheckout -> service.save(carCheckout, 0L, 0L));
//
//        // Then
//        //...IT SHOULD VERIFY THERE THE CHECK_OUT CARS WAS CHECK_OUT AND CAPTURE THE CHECK_OUT
//        verify(repo, times(carList.size())).save(argumentCaptor.capture());
//        //...IT SHOULD RETURN THE CAPTURE VALUES
//        List<CarCheckout> capture = argumentCaptor.getAllValues();
//        //...IT SHOULD RETURN A LIST OF CHECK_OUT CARS, AND THE LIST CONTAIN
//        // THE THIRD_CHECKOUT AND DOES NOT CONTAIN SECOND_CHECKOUT AND THE LAST_CHECKOUT
//        assertThat(service.findCheckOutByCarYear(capture, 2021))
//                .asList()
//                .hasSizeGreaterThan(0)
//                .satisfies(checks ->  assertThat(checks)
//                        .usingRecursiveComparison()
//                        .isNotNull());
//    }
//
//    @Test
//    void itShouldFindCheckOutByCarBrandAndModel() {
//        // Given...GIVEN CHECK_OUT INFORMATION
//        List<Car> carList = fakeData.carList();
//        List<CarCheckout> carCheckoutList = fakeData.carCheckoutList();
//
//        int[] index = {0};
//        carCheckoutList.forEach(checkout -> checkout.setCarCheckOut(carList.get(index[0]++)));
//
//        // When ...IT SHOULD SAVE THE CHECK_OUT LIST OF CARS
//        carCheckoutList.forEach(carCheckout -> service.save(carCheckout, 0L, 0L));
//
//        // Then
//        //...IT SHOULD VERIFY THE LIST CHECK_OUT CAR WAS SAVE, AND CAPTURE THE CHECK_OUT
//        verify(repo, times(carList.size())).save(argumentCaptor.capture());
//        //...GET ALL THE CHECK_OUT CAR
//        List<CarCheckout> capture = argumentCaptor.getAllValues();
//        //...IT SHOULD RETURN A LIST OF CHECK_OUT CARS, AND THE LIST SHOULD CONTAIN
//        // CAR WITH GIVEN BRAND AND MODEL BEING PASS
//        assertThat(service.findCheckOutByCarBrandAndModel(capture, "Ford", "Crown Victoria"))
//                .asList()
//                .containsOnly(capture.get(0).getCarCheckOut())
//                .isNotEmpty()
//                .hasSize(1);
//
//    }
//
//    @Test
//    void itShouldThrowExceptionWhenTheListIsEmptyForFindCarByCarBrand() {
//        // Given
//        CarCheckIn firstCheckOut = new CarCheckIn();
//        List<CarCheckout> list = new ArrayList<>();
//
//        firstCheckOut.setCheckInID(53778288L);
//        firstCheckOut.setCar(new Car(836622L, "Nissan", "37Z", "Open Top", 2004));
//        // When
//
//        // Then
//        //...IT SHOULD THROW EXCEPTION, SINCE EMPTY LATS IS BEING PASS
//        assertThatThrownBy(() -> service.findCheckOutByCarBrand(list, firstCheckOut.getCar().getBrand()))
//                .hasMessageContaining("No car in the checkin database")
//                .isInstanceOf(IllegalStateException.class);
//    }
//
//    @Test
//    void itShouldThrowExceptionWhenCarBrandNameBeingPassIsEmpty() {
//        // Given...GIVEN CHECK_OUT INFORMATION
//        List<Car> carList = fakeData.carList();
//        List<CarCheckout> carCheckoutList = fakeData.carCheckoutList();
//
//        int[] index = {0};
//        carCheckoutList.forEach(checkout -> checkout.setCarCheckOut(carList.get(index[0]++)));
//
//        // When ...IT SHOULD SAVE THE CHECK_OUT LIST OF CARS
//        carCheckoutList.forEach(carCheckout -> service.save(carCheckout, 0L, 0L));
//
//        // Then
//        //...IT SHOULD VERIFY THE LIST CHECK_OUT CAR WAS SAVE, AND CAPTURE THE CHECK_OUT
//        verify(repo, times(carList.size())).save(argumentCaptor.capture());
//        //...GET ALL THE CHECK_OUT CAR
//        List<CarCheckout> capture = argumentCaptor.getAllValues();
//        // Then
//        //...IT SHOULD THROW EXCEPTION, NO BRAND NAME IS WAS PASS
//        assertThatThrownBy(() -> service.findCheckOutByCarBrand(carCheckoutList, ""))
//                .hasMessageContaining(String.format("Value for car brand is [%s] ", ""))
//                .isInstanceOf(IllegalArgumentException.class);
//    }
//
//    @Test
//    void itShouldThrowExceptionWhenTheListIsEmptyForFindCarByCarYear() {
//        // Given
//        CarCheckIn firstCheckOut = new CarCheckIn();
//        List<CarCheckout> list = new ArrayList<>();
//
//        firstCheckOut.setCheckInID(53778288L);
//        firstCheckOut.setCar(new Car(836622L, "Nissan", "37Z", "Open Top", 2004));
//        // When
//
//        // Then
//        //...IT SHOULD THROW EXCEPTION, LIST IS EMPTY
//        assertThatThrownBy(() -> service.findCheckOutByCarYear(list, firstCheckOut.getCar().getYear()))
//                .hasMessageContaining("No car in the checkin database")
//                .isInstanceOf(IllegalStateException.class);
//    }
//
//    @Test
//    void itShouldThrowExceptionWhenCarYearIsZero() {
//        // Given...GIVEN CHECK_OUT INFORMATION
//        List<Car> carList = fakeData.carList();
//        List<CarCheckout> carCheckoutList = fakeData.carCheckoutList();
//
//        int[] index = {0};
//        carCheckoutList.forEach(checkout -> checkout.setCarCheckOut(carList.get(index[0]++)));
//
//        // When ...IT SHOULD SAVE THE CHECK_OUT LIST OF CARS
//        carCheckoutList.forEach(carCheckout -> service.save(carCheckout, 0L, 0L));
//
//        // Then
//        //...IT SHOULD VERIFY THE LIST CHECK_OUT CAR WAS SAVE, AND CAPTURE THE CHECK_OUT
//        verify(repo, times(carList.size())).save(argumentCaptor.capture());
//        //...GET ALL THE CHECK_OUT CAR
//        List<CarCheckout> capture = argumentCaptor.getAllValues();
//        // Then
//        //...IT SHOULD THROW EXCEPTION, SINCE 0 IS BEING PASS FOR CAR YEAR
//        assertThatThrownBy(() -> service.findCheckOutByCarYear(carCheckoutList, 0))
//                .hasMessageContaining(String.format("Value for car year is [%s] ", 0))
//                .isInstanceOf(IllegalArgumentException.class);
//    }
//
//    @Test
//    void itShouldThrowExceptionWhenTheBrandOrModelIsEmptyForFindCarByCarBrandAndModel() {
//        // Given...GIVEN CHECK_OUT INFORMATION
//        List<Car> carList = fakeData.carList();
//        List<CarCheckout> carCheckoutList = fakeData.carCheckoutList();
//
//        int[] index = {0};
//        carCheckoutList.forEach(checkout -> checkout.setCarCheckOut(carList.get(index[0]++)));
//
//        // When ...IT SHOULD SAVE THE CHECK_OUT LIST OF CARS
//        carCheckoutList.forEach(carCheckout -> service.save(carCheckout, 0L, 0L));
//
//        // Then
//        //...IT SHOULD VERIFY THE LIST CHECK_OUT CAR WAS SAVE, AND CAPTURE THE CHECK_OUT
//        verify(repo, times(carList.size())).save(argumentCaptor.capture());
//        //...GET ALL THE CHECK_OUT CAR
//        List<CarCheckout> capture = argumentCaptor.getAllValues();
//        // Then
//        //...IT SHOULD THROW EXCEPTION, NO VALUE IS BEING PASS FOR MODEL
//        assertThatThrownBy(() -> service.findCheckOutByCarBrandAndModel(carCheckoutList, carList.get(1).getBrand(), ""))
//                .hasMessageContaining(String.format("Value for car brand is [%s] and model is [%s]", carList.get(1).getBrand(), ""))
//                .isInstanceOf(IllegalArgumentException.class);
//    }
//
//    @Test
//    void itShouldThrowExceptionWhenTheListIsEmptyForFindCarByCarBrandAndModel() {
//        // Given
//        CarCheckIn firstCheckOut = new CarCheckIn();
//        List<CarCheckout> list = new ArrayList<>();
//
//        firstCheckOut.setCheckInID(53778288L);
//        firstCheckOut.setCar(new Car(836622L, "Nissan", "37Z", "Open Top", 2004));
//        // When
//
//        // Then
//        //...IT SHOULD THROW EXCEPTION, LIST IS EMPTY
//        assertThatThrownBy(() -> service.findCheckOutByCarBrandAndModel(list, "Nissan", "37Z"))
//                .hasMessageContaining("No car in the checkin database")
//                .isInstanceOf(IllegalStateException.class);
//    }
//
//    @Test
//    void itShouldThrowIllegalStateExceptionForDeletingCheckout() {
//        // Given...GIVING CHECKOUT ID
//        long checkOutID = 0L;
//        // When
//        // Then...IT SHOULD THROW ILLEGAL STATE EXCEPTION FOR NOT FINDING CHECKOUT
//        assertThatThrownBy(() -> service.deleteCheckOut(checkOutID))
//                .hasMessageContaining(String.format("There is no check out with an ID [ %s ]", checkOutID))
//                .isInstanceOf(IllegalStateException.class);
//    }
//
//    @Test
//    void itShouldReturnEmptyCarForFindingCheckoutByID() {
//        // Given...GIVING CHECKOUT ID
//        long checkOutID = 0L;
//        // When
//        // Then...IT SHOULD THROW ILLEGAL STATE EXCEPTION FOR NOT FINDING CHECKOUT
//        assertThat(service.findCheckOutByID(checkOutID))
//                .usingRecursiveComparison()
//                .isEqualTo(new Car())
//                .isInstanceOf(Car.class);
//    }
//}