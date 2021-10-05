package com.ebki.service;

import com.ebki.model.Car;
import com.ebki.model.CarCheckout;
import com.ebki.repository.CheckOutRepo;
import com.ebki.request.CarCheckOutRequest;
import com.ebki.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarCheckoutService {

    private final CheckOutRepo repository;

    @Autowired
    public CarCheckoutService(CheckOutRepo repository) {
        this.repository = repository;
    }

    public void checkOutCar(CarCheckout checkout) {

        CarCheckOutRequest request = new CarCheckOutRequest(checkout);
        Car requestCar = request.getCheckout().getCar();

        Optional<CarCheckout> optional = repository.findById(request.getCheckout().getCheckoutID());

        if (optional.isPresent()) {
            Car car = optional.get().getCar();
            if (car.getBrand().equals(requestCar.getBrand()) && car.getModel().equals(requestCar.getModel())) {
                return;
            }
            Util.throwIllegalStateException(String.format("Car [%s] with Vin number [%s] is already checkout ",
                    car.getBrand(), car.getCarVinNumber()));
        }

        if (request.getCheckout().getCheckoutID() == null) {
            request.getCheckout().setCheckoutID(Util.id(672415261));
        }
        repository.save(request.getCheckout());
    }
}
