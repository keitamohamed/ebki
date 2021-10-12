package com.ebki.service;

import com.ebki.repository.CarRepo;
import com.ebki.model.Car;
import com.ebki.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarService {

    private final CarRepo carRepository;

    @Autowired
    public CarService(CarRepo carRepository) {
        this.carRepository = carRepository;
    }

    public void registerNewCar(Car car) {

        Optional<Car> optionalCar = carRepository.selectCarByVinNumber(car.getCarVinNumber());
        if (optionalCar.isPresent()) {
            Car dbCar = optionalCar.get();
            if (dbCar.getModel().equals(car.getModel())) {
                return;
            }
            Util.throwIllegalStateException(String.format("Vin number [%s] is taken", car.getCarVinNumber()));
        }

        if (car.getCarVinNumber() == null) {
            car.setCarVinNumber(Util.generateID(999999999));
        }
        carRepository.save(car);
    }


}
