package com.ebki.controller;

import com.ebki.config.AwsConfig;
import com.ebki.model.Address;
import com.ebki.model.Authenticate;
import com.ebki.model.Driver;
import com.ebki.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ebik/driver/**")
@CrossOrigin("*")
public class DriverController {

    private final DriverService service;

    public DriverController(DriverService driverService) {
        this.service = driverService;
    }

    @PostMapping(
            value = {"/add"},
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void registerNewDriver(@RequestBody Driver driver) {
        service.save(driver);
    }

    @PostMapping(value = {"add_address/{id}"},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addNewAddress(@RequestBody Address address, @PathVariable("id") Long driverID, HttpServletResponse response) {
        service.saveAddress(address, driverID, response);
    }

    @GetMapping(value = {"/get_drivers"})
    public List<Driver> getDrivers() {
        return service.drivers();
    }

    @GetMapping(value = {"/find_by_id/{id}"})
    public Optional<Driver> findByID(@PathVariable("id") Long id) {
        return service.findDriverByID(id);
    }

    @GetMapping(value = {"/find_by_username/{username}"})
    public Driver findDriverByUsername(@PathVariable("username") String username) {
        return service.findByUsername(username);
    }

    @PutMapping(value = {"/update/{id}"})
    public Optional<Driver> updateDriver(@RequestBody Driver driver, @PathVariable("id") Long id) {
        return service.updateDriver(id, driver);
    }

    @GetMapping(value = {"/find_address/{id}"})
    public Address address (@PathVariable("id") Long id) {
        Optional<Address> address = service.findAddress(id);
        return address.orElseGet(Address::new);
    }

    @PutMapping(value = {"/update_password/{id}"})
    public void updatePassword(@RequestBody Authenticate authenticate,
                               @PathVariable("id") Long id,
                               HttpServletResponse response) {
        service.updatePassword(authenticate, id, response);
    }

    @PutMapping(value = {"/update_address/{id}"})
    public void updateAddress(@RequestBody Address address,
                              @PathVariable("id") Long id,
                              HttpServletResponse response) {
        service.updateAddress(address, id, response);
    }

    @DeleteMapping(value = {"/delete/{id}"})
    public void deleteDriver(@PathVariable("id") Long id) {
        service.deleteDriver(id);
    }

    @DeleteMapping(value = {"/delete_address_by_id/{id}"})
    public void deleteAddress(@PathVariable("id") Long id, HttpServletResponse response) {
        service.deleteAddress(id, response);
    }

    @GetMapping(value = {"/email/{email}"})
    public Optional<Driver> findDriverByEmail(@PathVariable("email") String email) {
        return service.findDriverByEmail(email);
    }

}
