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

        Set<Address> address = driver.getAddress();
        address.forEach(address1 -> {
            if (address1.getAddressID() == null) {
                address1.setAddressID(Util.generateID(99999999));
            }
        });
        driverRepository.save(driver);
    }

    // Just wrote this code. Need to add test
    public Optional<Driver> findDriverByEmail(String email) {
        return driverRepository.findDriverByEmailAddress(email);
    }

}
