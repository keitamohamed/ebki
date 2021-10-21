package com.ebki.service;

import com.ebki.model.Car;
import com.ebki.model.CarCheckout;
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
    private CarService service;

    @Autowired
    public CheckoutService(CheckOutRepo repository) {
        this.repository = repository;
    }

    public void save(CarCheckout checkout) {

        CheckOut request = new CheckOut(checkout);
        Car requestCar = request.getCheckout().getCarCheckOut();

        if (request.getCheckout().getCheckoutID() == null) {
            request.getCheckout().setCheckoutID(Util.generateID(672415261));
        }

        Optional<CarCheckout> optional = repository.findById(request.getCheckout().getCheckoutID());

        if (optional.isPresent()) {
            Car car = optional.get().getCarCheckOut();
            if (car.getBrand().equals(requestCar.getBrand()) && car.getModel().equals(requestCar.getModel())) {
                return;
            }
            Util.throwIllegalStateException(String.format("Car [ %s ] with vin number [ %s ] is already checkout ",
                    car.getBrand(), car.getVin()));
        }
        repository.save(request.getCheckout());
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

    public Car findCheckOutByID(Long checkOutID) {
        Optional<CarCheckout> optional = repository.findById(checkOutID);
        if (optional.isEmpty())
        {
            return new Car();
        }
        return optional.get().getCarCheckOut();
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
