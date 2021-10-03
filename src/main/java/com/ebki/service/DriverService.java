package com.ebki.service;

import com.ebki.interfaces.DriverRepository;
import com.ebki.model.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class DriverService {

    private final DriverRepository driverRepository;

    @Autowired
    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public void registerNewDriver(Driver driver) {
        String driverEmail = driver.getEmail();

        Optional<Driver> dbDriver = driverRepository.selectDriverByEmail(driverEmail);

        if (dbDriver.isPresent()) {
            Driver getDriver = dbDriver.get();
            if (getDriver.getFirstName().equals(driver.getFirstName())) {
                return;
            }
            throw new IllegalStateException(String.format("Email address [%s] is taken", driverEmail));
        }

        if (driver.getDriverID() == null) {
            driver.setDriverID(id());
        }
        driverRepository.save(driver);
    }

    private Long id() {
        Random random = new Random();
        return (long) random.nextInt(999999999);
    }

}
