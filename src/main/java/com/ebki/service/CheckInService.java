package com.ebki.service;

import com.ebki.model.Car;
import com.ebki.model.CarCheckIn;
import com.ebki.model.CarCheckout;
import com.ebki.model.Driver;
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
    private final DriverService driverService;
    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    public CheckInService(CheckInRepo repo, DriverService driverService) {
        this.repo = repo;
        this.driverService = driverService;
    }

    public void save(CarCheckIn checkIn, Long driverID, Long checkoutID) {

        CheckIn checkInReq = new CheckIn(checkIn);
        setCheckInID(checkIn);

        Optional<CarCheckIn> optional = repo.findById(checkIn.getCheckInID());
        Optional<Driver> optionalDriver = driverService.findDriverByID(driverID);
        Optional<CarCheckout> optionalCarCheckout = checkoutService.findCarCheckoutByID(checkoutID);

        if (optional.isPresent()) {
            Car car = optional.get().getCar();
            if (car.equals(checkIn.getCar())) {
                return;
            }
            throw new IllegalStateException(String.format("CheckIn with ID [%s] already exists",
                    checkIn.getCheckInID()));
        }

        throwExceptionIfDriverOrCheckoutCarIsEmpty(driverID, checkoutID, checkInReq, optionalDriver, optionalCarCheckout);
        repo.save(checkInReq.getCheckIn());
    }

    private void throwExceptionIfDriverOrCheckoutCarIsEmpty(
            Long driverID, Long checkoutID, CheckIn checkInReq,
            Optional<Driver> optionalDriver, Optional<CarCheckout> optionalCarCheckout) {
        if (optionalDriver.isEmpty()) {
            Util.throwIllegalStateException(String.format("No driver found with an ID [ %s ]", driverID));
        }
        if (optionalCarCheckout.isEmpty()) {
            Util.throwIllegalStateException(String.format("Checkout ID does not exists [ %s ]", checkoutID));
        }
        checkInReq.getCheckIn().setCarCheckIn(optionalDriver.get());
        checkInReq.getCheckIn().setCar(optionalCarCheckout.get().getCarCheckOut());
    }

    // Return car with matching brand and model
    public List<CarCheckIn> findCheckInByCarBrandAndModel(List<CarCheckIn> checkInList, String brand, String model) {

        if (checkInList.isEmpty()) {
            Util.throwIllegalStateException("No car in the checkin database");
        }
        if (brand.isEmpty() || model.isEmpty()) {
            throw new IllegalArgumentException(
                    String.format("Value for car brand is [%s] and model is [%s]", brand, model));
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
        if (Util.isIntegerValueZero(year)) {
            throw new IllegalArgumentException(
                    String.format("Value for car year is [%s] ", year));
        }

        return carCheckInList
                .stream()
                .filter(checkIn -> checkIn.getCar().compareTo(year) == 0)
                .collect(toList());
    }

    private void setCheckInID(CarCheckIn checkIn) {
        if (checkIn.getCheckInID() == null) {
            checkIn.setCheckInID(Util.generateID(672415261));
        }
    }
}
