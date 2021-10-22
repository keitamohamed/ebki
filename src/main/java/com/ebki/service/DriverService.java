package com.ebki.service;

import com.ebki.model.Address;
import com.ebki.model.Driver;
import com.ebki.repository.DriverRepo;
import com.ebki.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DriverService {

    private final DriverRepo repository;

    @Autowired
    public DriverService(DriverRepo driverRepository) {
        this.repository = driverRepository;
    }

    public void save(Driver driver) {
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
        repository.save(driver);
    }

    private void setAddressIDIfNull(Driver driver) {
        Set<Address> address = driver.getAddress();
        address.forEach(a -> {
            if (a.getAddressID() == null) {
                a.setAddressID(Util.generateID(99999999));
            }
        });
    }

    public List<Driver> drivers() {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(
                        repository.findAll().iterator(),
                        Spliterator.ORDERED), false)
                .collect(Collectors.toList());
    }

    public void deleteDriver(Long id) {
        if (Util.isLongValueZero(id)) {
            Util.throwIllegalStateException(String.format("[ %s ] is an invalid driver id.", id));
        }
        repository.deleteById(id);
    }

    // Just wrote this code. Need to add test
    public Optional<Driver> findDriverByEmail(String email) {
        return repository.findDriverByEmailAddress(email);
    }

}
