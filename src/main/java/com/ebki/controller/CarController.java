package com.ebki.controller;

import com.ebki.model.Car;
import com.ebki.request.CarRegistration;
import com.ebki.service.CarService;
import io.micrometer.core.ipc.http.HttpSender;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
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
    public void registerCar(@RequestBody Car car, HttpServletResponse response) {
        CarRegistration registration = new CarRegistration(car);
        response.setHeader("vin", service.save(registration.getCar()));
    }

    @PostMapping(path = {"/save-car-image/{id}"},
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void saveCarImage(
            @PathVariable("id") Long id,
            @RequestParam("file") MultipartFile file) {
        service.saveCarImage(id, file);
    }

    @GetMapping(value = {"/download-image/{id}"})
    public byte[] downloadImage(@PathVariable("id") Long id) {
        return service.downloadImage(id);
    }

    @DeleteMapping(value = "/delete-image/{vin}")
    public void deleteImage(@PathVariable("vin") Long vin) {
        service.deleteFile(vin);
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
    }

    @PutMapping(value = "/update/{vin}")
    public void update(@RequestBody Car car, @PathVariable("vin") Long vin) {
        service.updateCar(vin, car);
    }
}
