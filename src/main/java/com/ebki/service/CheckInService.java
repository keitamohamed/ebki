package com.ebki.service;

import com.ebki.model.Car;
import com.ebki.model.CarCheckIn;
import com.ebki.repository.CheckInRepo;
import com.ebki.request.CheckIn;
import com.ebki.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class CheckInService {

    private final CheckInRepo repo;

    @Autowired
    public CheckInService(CheckInRepo repo) {
        this.repo = repo;
    }

    public void save(CarCheckIn checkIn) {

        CheckIn checkInRegistration = new CheckIn(checkIn);

        Optional<CarCheckIn> optional = repo.findById(checkIn.getCheckInID());

        if (optional.isPresent()) {
            Car car = optional.get().getCar();
            if (car.equals(checkIn.getCar())) {
                return;
            }
            throw new IllegalStateException(String.format("CheckIn with ID [%s] already exists",
                    checkIn.getCheckInID()));
        }
        repo.save(checkInRegistration.getCheckIn());
    }

    // Return car with matching brand and model
    public List<CarCheckIn> findCheckInByCarBrandAndModel(List<CarCheckIn> checkInList, String brand, String model) {

        if (checkInList.isEmpty()) {
            Util.throwIllegalStateException("No car in the checkin database");
        }
        return checkInList
                .stream()
                .filter(checkIn ->
                        checkIn.getCar().getBrand().equals(brand) &&
                        checkIn.getCar().getModel().equals(model))
                .collect(toList());
    }

    public List<CarCheckIn> findCheckInByCarBrand(List<CarCheckIn> carCheckInList, String brand) {

        if (carCheckInList.isEmpty()) {
            Util.throwIllegalStateException("No car in the checkin database");
        }
        if (brand.isEmpty()) {
            throw new IllegalArgumentException(
                    String.format("Value for car brand is [%s] ", brand));
        }

        return carCheckInList
                .stream()
                .filter(checkIn -> checkIn.getCar().getBrand().equals(brand))
                .collect(Collectors.toList());

    }

    public List<CarCheckIn> findCheckInByCarYear(List<CarCheckIn> carCheckInList, int year) {

        if (carCheckInList.isEmpty()) {
            Util.throwIllegalStateException("No car in the checkin database");
        }
        if (year == 0) {
            throw new IllegalArgumentException(
                    String.format("Value for car year is [%s] ", year));
        }

        return carCheckInList
                .stream()
                .filter(checkIn -> checkIn.getCar().compareTo(year) == 0)
                .collect(toList());
    }
}
