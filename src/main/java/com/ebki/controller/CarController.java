package com.ebki.controller;

import com.ebki.model.Car;
import com.ebki.request.CarRegistration;
import com.ebki.service.CarService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ebik/car/**")
@CrossOrigin("*")
public class CarController {
    private final CarService service;

    public CarController(CarService service) {
        this.service = service;
    }

    @RequestMapping(value = {"/register_new_car"},
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    public void registerCar(@RequestBody Car car) {
        CarRegistration registration = new CarRegistration(car);
        service.save(registration.getCar());
    }

    @GetMapping(path = {"/brand/{brand}"})
    public List<Car> findCarByBrand(@PathVariable("brand") String brand) {
        return service.findCarByBand(service.findAll(), brand);
    }

    @GetMapping(path = {"/year/{year}"})
    public List<Car> findCarByYear(@PathVariable("year") int year) {
        return service.findCarByYear(service.findAll(), year);
    }

    @GetMapping(path = {"/find_by_brand_mode_year/{brand}/{model}/{year}"})
    public List<Car> findCarByBrandModelYear(
            @PathVariable("brand") String brand,
            @PathVariable("model") String model,
            @PathVariable("year") int year) {
        return service.findCarByBrandModelAndYear(service.findAll(), brand, model, year);
    }

    @GetMapping(value = {"/find_car_vin/{vin}"})
    public Optional<Car> getCar(@PathVariable("vin") Long id) {
        return service.findCarById(id);
    }
}
