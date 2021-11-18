package com.ebki.controller;

import com.ebki.model.Car;
import com.ebki.request.CarRegistration;
import com.ebki.service.CarService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ebik/car/**")
@CrossOrigin("*")
public class CarController {

    private final CarService service;

    public CarController(CarService service) {
        this.service = service;
    }

    @RequestMapping(value = {"/add"},
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    public void registerCar(@RequestBody Car car) {
        CarRegistration registration = new CarRegistration(car);
        service.save(registration.getCar());
    }

    @GetMapping(value = {"/carList"})
    public List<Car> carList() {
        return service.findAll();
    }

    @GetMapping(path = {"/brand/{brand}"})
    public List<Car> findCarByBrand(@PathVariable("brand") String brand) {
        return service.findCarByBand(service.findAll(), brand);
    }

//    public List<Car> findCarByModel(@PathVariable("model") String model) {
//        return service.find
//    }

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

    @GetMapping(value = {"/vin/{vin}"})
    public List<Car> getCar(@PathVariable("vin") Long id) {
        return  service.findCarById(id)
                .stream()
                .collect(Collectors.toList());
//        return service.findCarById(id);
    }

    @PutMapping(value = "/update/{vin}")
    public void update(@RequestBody Car car, @PathVariable("vin") Long vin) {
        service.updateCar(vin, car);
    }
}
