package com.ebki.controller;

import com.ebki.model.Driver;
import com.ebki.service.DriverService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ebik/driver/**")
@CrossOrigin("*")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @RequestMapping(value = {"/add_new_driver"},
            method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void registerNewDriver(@RequestBody Driver driver) {
        driverService.registerNewDriver(driver);
    }
}
