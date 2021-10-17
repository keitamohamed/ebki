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

    public void save(Car car) {

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

    public List<Car> findCarByBand(List<Car> carList, String brand) {
        if (brand.isEmpty()) {
            Util.throwIllegalStateException(String.format("Car brand [ %s ] cannot be null or empty", brand));
        }
        if (carList.size() == 0) {
            Util.throwIllegalStateException(String.format("Car list [ %s ] is empty", 0));
        }
        return carList
                .stream()
                .filter(car -> car.getBrand().equals(brand))
                .collect(Collectors.toList());
    }

    public List<Car> findCarByYear(List<Car> carList, int year) {
        if (Util.isIntegerValueZero(year)) {
            Util.throwIllegalStateException(String.format("Car year [ %s ] cannot be 0 or empty", year));
        }
        if (carList.size() == 0) {
            Util.throwIllegalStateException(String.format("Car list is [ %s ]. No cars in database", year));
        }
        return carList
                .stream()
                .filter(car -> car.compareTo(year) == 0)
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
