package com.ebki.controller;

import com.ebki.model.Car;
import com.ebki.request.CarRegistration;
import com.ebki.service.CarService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
        System.out.println("Brand is " + registration.getCar().getBrand());
        service.save(registration.getCar());
    }

    @PostMapping(value = {"/brand/{brand}"},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Car> findCarByBrand(@PathVariable("brand") String brand) {
        System.out.println("Value being pass is " + brand);
        List<Car> carList = new ArrayList<>();
        carList.add(new Car(783211L, "Toyota", "4X4", "Truck", 2012));
        return carList;
    }
}
