//package com.ebki.repository;
//
//import com.ebki.TestData;
//import com.ebki.model.Car;
//import com.ebki.request.CarRegistration;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.dao.DataIntegrityViolationException;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
//
//@DataJpaTest(
//        properties = {
//                "spring.jpa.properties.javax.persistence.validation.mode=none"
//        }
//)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class CarRepositoryTest {
//    @Autowired
//    private CarRepo carRepository;
//    private final TestData data = new TestData();
//
//    @Test
//    void itShouldNotSelectCarWhenVinNumDoesNotExists() {
//        // Given
//        long vinNum = 782341654L;
//        // When
//        Optional<Car> optionalCar = carRepository.selectCarByVinNumber(vinNum);
//        // Then
//        assertThat(optionalCar).isNotPresent();
//    }
//
//    @Test
//    void itShouldSaveCar() {
//        // Given
//        Car car = new Car(672635511L, "Nissan", "Maxima", "Open Top", 2018);
//        CarRegistration request = new CarRegistration(car);
//        // When
//        carRepository.save(request.getCar());
//        // Then
//        Optional<Car> optionalCar = carRepository.selectCarByVinNumber(car.getVin());
//        assertThat(optionalCar)
//                .isPresent()
//                .hasValueSatisfying(c -> {
//                    assertThat(c)
//                            .usingRecursiveComparison()
//                            .isEqualTo(car);
//                });
//    }
//
//    @Test
//    void itShouldFindCarByBrandAndModelYear() {
//        // Given
//        long id = 672635511L;
//        Car car = new Car(id, "Nissan", "Maxima", "Open Top", 2012);
//        CarRegistration request = new CarRegistration(car);
//        // When
//        carRepository.save(request.getCar());
//        // Then
//        Optional<Car> optionalCar = carRepository.findCarByBrandAndModelYear(car.getBrand(), car.getYear());
//        assertThat(optionalCar)
//                .isPresent()
//                .hasValueSatisfying(c -> {
//                    assertThat(c)
//                            .usingRecursiveComparison()
//                            .isEqualTo(car);
//                });
//
//    }
//
//    @Test
//    void itShouldSelectCarByBrandAndModelAndModelYear() {
//        // Given
//        Car car = new Car(67231631L, "Nissan", "Maxima", "Open Top", 2016);
//        CarRegistration request = new CarRegistration(car);
//        // When
//        carRepository.save(request.getCar());
//        // Then
//        List<Car> optionalCar = carRepository.findCarByBrandAndModelAndModelYear(car.getBrand(), car.getModel(), car.getYear());
//        assertThat(optionalCar)
//                .isNotNull()
//                .asList()
//                .hasSizeGreaterThan(0);
//    }
//
//    @Test
//    void itShouldNotSaveCarWhenBrandIsNull() {
//        // Given
//        Car car = new Car(672635511L, null, "Maxima", "Open top", 2019);
//        CarRegistration request = new CarRegistration(car);
//        // When
//        // Then
//        assertThatThrownBy(() -> carRepository.save(request.getCar()))
//                .hasMessageContaining("not-null property references a null or transient value :")
//                .isInstanceOf(DataIntegrityViolationException.class);
//    }
//
//    @Test
//    void itShouldFindCarByBrand() {
//        // Given...GIVEN CAR INFORMATION
//        List<Car> carList = data.carList();
//        // When...IT SHOULD SAVE LIST OF CAR
//        carList.forEach(car -> {
//            CarRegistration registration = new CarRegistration(car);
//            carRepository.save(registration.getCar());
//        });
//        // Then...IT SHOULD FIND CAR BY BRAND AND CHECK THE LIST SIZE IS JUST ONE
//        List<Car> findCarByBrand = carRepository.findCarByBrand("Nissan");
//        assertThat(findCarByBrand)
//                .isNotNull()
//                .asList()
//                .hasSize(1);
//    }
//
//    @Test
//    void itShouldFindCarByYear() {
//        // Given...GIVEN CAR INFORMATION
//        List<Car> carList = data.carList();
//        // When...IT SHOULD SAVE LIST OF CAR
//        carList.forEach(car -> {
//            CarRegistration registration = new CarRegistration(car);
//            carRepository.save(registration.getCar());
//        });
//        // Then...IT SHOULD FIND CAR BY Year AND CHECK THE LIST TWO IS JUST ONE
//        List<Car> findCarByBrand = carRepository.findCarByYear(2016);
//        assertThat(findCarByBrand)
//                .asList()
//                .isNotNull()
//                .hasSize(2);
//        assertThat(findCarByBrand).satisfies(cars -> {
//            assertThat(cars.get(1))
//                    .usingRecursiveComparison()
//                    .isEqualTo(carList.get(carList.size() - 1));
//            assertThat(cars.get(0))
//                    .usingRecursiveComparison()
//                    .isEqualTo(carList.get(2));
//        });
//    }
//
//    @Test
//    void itShouldFindCarByBrandModelAndYear() {
//        // Given...GIVEN CAR INFORMATION
//        List<Car> carList = data.carList();
//        // When...IT SHOULD SAVE LIST OF CAR
//        carList.forEach(car -> {
//            CarRegistration registration = new CarRegistration(car);
//            carRepository.save(registration.getCar());
//        });
//        // Then...IT SHOULD FIND CAR BY Brand, Model, and Year AND CHECK THE LIST SIZE IS JUST ONE
//        Car car = carList.get(1);
//        List<Car> findCarByBrandModelAndYear = carRepository.findCarByBrandAndModelAndModelYear(car.getBrand(), car.getModel(), car.getYear());
//        assertThat(findCarByBrandModelAndYear)
//                .isNotNull()
//                .asList()
//                .hasSizeGreaterThan(0)
//                .satisfies(cars -> {
//                    assertThat(cars.get(0))
//                            .usingRecursiveComparison()
//                            .isEqualTo(car);
//                });
//
//    }
//}