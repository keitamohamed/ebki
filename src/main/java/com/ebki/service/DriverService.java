package com.ebki.service;

import com.ebki.model.Address;
import com.ebki.model.Authenticate;
import com.ebki.model.Driver;
import com.ebki.model.Role;
import com.ebki.repository.AuthRepo;
import com.ebki.repository.DriverRepo;
import com.ebki.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DriverService {

    private final DriverRepo repository;
    @Autowired
    private AuthRepo authRepo;
    @Autowired
    private CheckoutService checkoutService;
    @Autowired
    private CarService carService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    public DriverService(DriverRepo driverRepository) {
        this.repository = driverRepository;
    }

    public void save(Driver driver) {
        System.out.println("Driver " + driver.getAuthenticate().getUsername());

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

        setAuthIDIfNull(driver);
        authenticateDriver(driver);
        encodePassword(driver);
        setAddressIDIfNull(driver);
        repository.save(driver);
    }

    public Driver findByUsername(String username) {
        Authenticate authenticate = authRepo.findByUsername(username);
        if (authenticate == null) {
            return new Driver();
        }
        return createNewDriver(authenticate.getAuthDriver());
    }

    private Driver createNewDriver(Driver driver) {
        Driver newDriver = new Driver();
        newDriver.setDriverID(driver.getDriverID());
        newDriver.setFirstName(driver.getFirstName());
        newDriver.setLastName(driver.getLastName());
        newDriver.setGender(driver.getGender());
        newDriver.setDob(driver.getDob());
        newDriver.setEmail(driver.getEmail());
        newDriver.setPhoneNum(driver.getPhoneNum());
        newDriver.setAddress(driver.getAddress());
        newDriver.setCheckout(driver.getCheckout());
        newDriver.setCarCheckIns(driver.getCarCheckIns());
        return newDriver;
    }

    private void setAuthIDIfNull(Driver driver) {
        System.out.println("Driver " + driver.getAuthenticate().getUsername());
        Authenticate authenticate = driver.getAuthenticate();
        if (authenticate.getAuthID() == null) {
            authenticate.setAuthID(Util.generateID(99999999));
        }
        setRoleIDIfNull(authenticate);
    }

    private void setRoleIDIfNull(Authenticate authenticate) {
        Role role = authenticate.getRole();
        if (role.getRoleID() == null) {
            role.setRoleID(Util.generateID(999999));
        }
    }

    private void authenticateDriver(Driver driver) {
        Authenticate authenticate = driver.getAuthenticate();
        authenticate.setAccountNonExpired(true);
        authenticate.setAccountNotLocked(true);
        authenticate.setCredentialsNonExpired(true);
        authenticate.setEnabled(true);
    }

    private void encodePassword(Driver driver) {
        Authenticate authenticate = driver.getAuthenticate();
        authenticate.setPassword(passwordEncoder.encode(authenticate.getPassword()));
    }

    private void setAddressIDIfNull(Driver driver) {
        Set<Address> address = driver.getAddress();
        address.forEach(a -> {
            if (a.getAddressID() == null) {
                a.setAddressID(Util.generateID(99999999));
            }
        });
    }

    public Optional<Driver> updateDriver(Long driverID, Driver driver) {
        if (repository.findById(driverID).isEmpty()) {
            Util.throwIllegalStateException(String.format("No Driver found with an ID = [ %s ]", driverID));
        }
        driver.setDriverID(driverID);
        repository.save(driver);
        return repository.findById(driverID);
    }

    public Optional<Driver> findDriverByID(Long id) {
        if (repository.findById(id).isEmpty()) {
            return Optional.empty();
        }
        return repository.findById(id);
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

    public Optional<Driver> findDriverByEmail(String email) {
        return repository.findDriverByEmailAddress(email);
    }

}
