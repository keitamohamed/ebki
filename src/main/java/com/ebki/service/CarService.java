package com.ebki.service;

import com.ebki.repository.CarRepo;
import com.ebki.model.Car;
import com.ebki.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {

    private final CarRepo repository;

    @Autowired
    public CarService(CarRepo carRepository) {
        this.repository = carRepository;
    }

    public void registerNewCar(Car car) {

        if (car.getVin() == null) {
            car.setVin(Util.generateID(999999999));
        }

        Optional<Car> optionalCar = repository.selectCarByVinNumber(car.getVin());
        if (optionalCar.isPresent()) {
            Car dbCar = optionalCar.get();
            if (dbCar.getModel().equals(car.getModel())) {
                return;
            }
            Util.throwIllegalStateException(String.format("Vin number [%s] is taken", car.getVin()));
        }
        repository.save(car);
    }

    public List<Car> findCarByRand(String brand) {
        if (brand.isEmpty()) {
            Util.throwIllegalStateException(String.format("Car brand [ %s ] cannot be null or empty", brand));
        }
        List<Car> carBrand = repository.findCarByBrand(brand);

        if (carBrand.size() == 0) {
            Util.throwIllegalStateException(String.format("Car brand [ %s ] does not exists", brand));
        }
        return carBrand
                .stream()
                .filter(car -> car.getBrand().equals(brand))
                .collect(Collectors.toList());
    }

    public List<Car> findCarByBrandModelAndYear(String brand, String model, int year) {

        if (brand.isEmpty() || model.isEmpty() ||
                Util.isIntegerValueZero(year)) {
            Util.throwIllegalStateException(String.format("Value enter for brand is [ %s ], " +
                    "value for model is [ %s ] and value enter for year is [ %s ]. " +
                    "Enter a valid for all field", brand, model, year));
        }
        return repository.findCarByBrandAndModelAndModelYear(brand, model, year);
    }


}
