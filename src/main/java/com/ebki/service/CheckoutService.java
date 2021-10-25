package com.ebki.service;

import com.ebki.model.Car;
import com.ebki.model.CarCheckout;
import com.ebki.model.Driver;
import com.ebki.repository.CheckOutRepo;
import com.ebki.request.CheckOut;
import com.ebki.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CheckoutService {

    private final CheckOutRepo repository;
    @Autowired
    private DriverService driverService;
    @Autowired
    private CarService carService;

    @Autowired
    public CheckoutService(CheckOutRepo repository) {
        this.repository = repository;
    }

    public void save(CarCheckout checkout, Long driverID, Long carID) {

        CheckOut request = new CheckOut(checkout);
        Car requestCar = request.getCheckout().getCarCheckOut();

        setCheckOutID(request.getCheckout());

        Optional<CarCheckout> optional = repository.findById(request.getCheckout().getCheckoutID());
        Optional<Driver> optionalDriver = driverService.findDriverByID(driverID);
        Optional<Car> carOptional = carService.findCarById(carID);

        if (optional.isPresent()) {
            Car car = optional.get().getCarCheckOut();
            if (car.getBrand().equals(
                    requestCar.getBrand())
                    && car.getModel().equals(requestCar.getModel())) {
                return;
            }
            Util.throwIllegalStateException(String.format("Car [ %s ] with vin number [ %s ] is already checkout ",
                    car.getBrand(), car.getVin()));
        }
        throwExceptionWhenDriverOrCarDoesNotExists(driverID, carID, optionalDriver, carOptional, request);
        repository.save(request.getCheckout());
    }

    public void throwExceptionWhenDriverOrCarDoesNotExists(
            Long driverID, Long carID, Optional<Driver> optionalDriver,
            Optional<Car> carOptional, CheckOut request) {
        if(optionalDriver.isEmpty()) {
            Util.throwIllegalStateException(String.format("No driver found with an ID [ %s ]", driverID));
        }
        if (carOptional.isEmpty()) {
            Util.throwIllegalStateException(String.format("No Car found with vin number [ %s ]", carID));
        }
        request.getCheckout().setCheckoutCar(optionalDriver.get());
        request.getCheckout().setCarCheckOut(carOptional.get());
    }

    private void setCheckOutID(CarCheckout carCheckout) {
        if (carCheckout.getCheckoutID() == null) {
            carCheckout.setCheckoutID(Util.generateID(672415261));
        }
    }

    public List<Car> findCheckOutByCarBrand(List<CarCheckout> carCheckoutList, String brand) {
        if (carCheckoutList.isEmpty()) {
            throw new IllegalStateException("No car in the checkin database");
        }
        if (brand.isEmpty()) {
            throw new IllegalArgumentException(String.format("Value for car brand is [%s] ", brand));
        }
        return carCheckoutList
                .stream()
                .map(CarCheckout::getCarCheckOut)
                .filter(car -> car.getBrand().equalsIgnoreCase(brand))
                .collect(Collectors.toList());
    }


    public List<Car> findCheckOutByCarYear(List<CarCheckout> carCheckoutList, int year) {
        if (carCheckoutList.isEmpty()) {
            throw new IllegalStateException("No car in the checkin database");
        }
        if (Util.isIntegerValueZero(year)) {
            throw new IllegalArgumentException(String.format("Value for car year is [%s] ", year));
        }
        return carCheckoutList
                .stream()
                .map(CarCheckout::getCarCheckOut)
                .filter(car -> car.compareTo(year) == 0)
                .collect(Collectors.toList());
    }

    public List<Car> findCheckOutByCarBrandAndModel(List<CarCheckout> carCheckoutList, String brand, String model) {
        if (carCheckoutList.isEmpty()) {
            Util.throwIllegalStateException("No car in the checkin database");
        }
        if (brand.isEmpty() || model.isEmpty()) {
            throw new IllegalArgumentException(
                    String.format("Value for car brand is [%s] and model is [%s]", brand, model));
        }
        return carCheckoutList
                .stream()
                .map(CarCheckout::getCarCheckOut)
                .filter(car -> car.getBrand().equalsIgnoreCase(brand)
                        && car.getModel().equalsIgnoreCase(model))
                .collect(Collectors.toList());
    }

    public void deleteCheckOut(Long checkoutID) {
        Optional<CarCheckout> checkout = findCarCheckoutByID(checkoutID);
        if (checkout.isEmpty()) {
            Util.throwIllegalStateException(String.format("There is no check out with an ID [ %s ]", checkoutID));
        }
        repository.deleteByCheckoutID(checkoutID);
    }

    public Car findCheckOutByID(Long checkOutID) {
        Optional<CarCheckout> optional = findCarCheckoutByID(checkOutID);
        return optional.isPresent() ? optional.get().getCarCheckOut() : new Car();
    }

    public Optional<CarCheckout> findCarCheckoutByID(Long checkOutID) {
        return repository.findById(checkOutID);
    }

    public List<Car> carCheckOut() {
        return checkoutList()
                .stream()
                .map(CarCheckout::getCarCheckOut)
                .collect(Collectors.toList());
    }

    public List<CarCheckout> checkoutList() {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(
                        repository.findAll()
                                .iterator(),
                        Spliterator.ORDERED),
                        false)
                .collect(Collectors.toList());
    }
}
