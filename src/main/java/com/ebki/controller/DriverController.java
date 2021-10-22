package com.ebki.controller;

import com.ebki.model.Driver;
import com.ebki.service.DriverService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ebik/driver/**")
@CrossOrigin("*")
public class DriverController {

    private final DriverService service;

    public DriverController(DriverService driverService) {
        this.service = driverService;
    }

    @PostMapping(
            value = {"/add_new_driver"},
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void registerNewDriver(@RequestBody Driver driver) {
        service.save(driver);
    }

    @DeleteMapping(value = {"/delete_driver/{id}"})
    public void deleteDriver(@PathVariable("id") Long id) {
        service.deleteDriver(id);
    }

    @GetMapping(value = {"/get_drivers"})
    public List<Driver> getDrivers() {
        return service.drivers();
    }
}
