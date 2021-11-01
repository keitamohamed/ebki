package com.ebki.controller;

import com.ebki.model.Driver;
import com.ebki.service.DriverService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
//        service.save(driver);
    }

    @GetMapping(value = {"/find_by_id/{id}"})
    public Optional<Driver> findByID(@PathVariable("id") Long id) {
        return service.findDriverByID(id);
    }

    @GetMapping(value = {"/get_drivers"})
    public List<Driver> getDrivers() {
        return service.drivers();
    }

    @PutMapping(value = {"/update/{id}"})
    public Optional<Driver> updateDriver(@RequestBody Driver driver, @PathVariable("id") Long id) {
        return service.updateDriver(id, driver);
    }

    @DeleteMapping(value = {"/delete/{id}"})
    public void deleteDriver(@PathVariable("id") Long id) {
        service.deleteDriver(id);
    }

}
