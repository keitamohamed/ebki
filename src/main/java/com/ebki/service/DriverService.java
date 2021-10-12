package com.ebki.service;

import com.ebki.model.Address;
import com.ebki.model.Driver;
import com.ebki.repository.DriverRepo;
import com.ebki.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class DriverService {

    private final DriverRepo driverRepository;

    @Autowired
    public DriverService(DriverRepo driverRepository) {
        this.driverRepository = driverRepository;
    }

    public void registerNewDriver(Driver driver) {
        if (findDriverByEmail(driver.getEmail()).isPresent()) {
            Driver searchResult = findDriverByEmail(driver.getEmail()).get();
            if (searchResult.getFirstName().equals(driver.getFirstName())) {
                return;
            }
            throw new IllegalStateException(String.format("Email address [%s] is taken", driver.getEmail()));
        }
        if (driver.getDriverID() == null) {
            driver.setDriverID(Util.generateID(999999999));
        }

        setAddressIDIfNull(driver);
        driverRepository.save(driver);
    }

    private void setAddressIDIfNull(Driver driver) {
        Set<Address> address = driver.getAddress();
        address.forEach(a -> {
            if (a.getAddressID() == null) {
                a.setAddressID(Util.generateID(99999999));
            }
        });
    }

    // Just wrote this code. Need to add test
    public Optional<Driver> findDriverByEmail(String email) {
        return driverRepository.findDriverByEmailAddress(email);
    }

}
