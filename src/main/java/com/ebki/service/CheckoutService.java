package com.ebki.service;

import com.ebki.model.Car;
import com.ebki.model.CarCheckout;
import com.ebki.repository.CheckOutRepo;
import com.ebki.request.CheckOut;
import com.ebki.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CheckoutService {

    private final CheckOutRepo repository;

    @Autowired
    public CheckoutService(CheckOutRepo repository) {
        this.repository = repository;
    }

    public void save(CarCheckout checkout) {

        CheckOut request = new CheckOut(checkout);
        Car requestCar = request.getCheckout().getCar();

        Optional<CarCheckout> optional = repository.findById(request.getCheckout().getCheckoutID());

        if (optional.isPresent()) {
            Car car = optional.get().getCar();
            if (car.getBrand().equals(requestCar.getBrand()) && car.getModel().equals(requestCar.getModel())) {
                return;
            }
            Util.throwIllegalStateException(String.format("Car [%s] with Vin number [%s] is already checkout ",
                    car.getBrand(), car.getVin()));
        }

        if (request.getCheckout().getCheckoutID() == null) {
            request.getCheckout().setCheckoutID(Util.generateID(672415261));
        }
        repository.save(request.getCheckout());
    }

    public List<CarCheckout> findCheckOutByCarBrand(List<CarCheckout> carCheckoutList, String brand) {
        if (carCheckoutList.isEmpty()) {
            throw new IllegalStateException("No car in the checkin database");
        }
        if (brand.isEmpty()) {
            throw new IllegalArgumentException(String.format("Value for car brand is [%s] ", brand));
        }
        return carCheckoutList
                .stream()
                .filter(checkout -> checkout.getCar().getBrand().equals(brand))
                .collect(Collectors.toList());
    }

    public List<CarCheckout> findCheckOutByCarYear(List<CarCheckout> carCheckoutList, int year) {
        if (carCheckoutList.isEmpty()) {
            throw new IllegalStateException("No car in the checkin database");
        }
        if (Util.isIntegerValueZero(year)) {
            throw new IllegalArgumentException(String.format("Value for car year is [%s] ", year));
        }
        return carCheckoutList
                .stream()
                .filter(checkout -> checkout.getCar().compareTo(year) == 0)
                .collect(Collectors.toList());
    }

    public List<CarCheckout> findCheckOutByCarBrandAndModel(List<CarCheckout> carCheckoutList, String brand, String model) {
        if (carCheckoutList.isEmpty()) {
            Util.throwIllegalStateException("No car in the checkin database");
        }
        if (brand.isEmpty() || model.isEmpty()) {
            throw new IllegalArgumentException(
                    String.format("Value for car brand is [%s] and model is [%s]", brand, model));
        }
        return carCheckoutList
                .stream()
                .filter(checkout -> checkout.getCar().getBrand().equals(brand)
                        && checkout.getCar().getModel().equals(model))
                .collect(Collectors.toList());
    }
}
