package com.ebki.controller;

import com.ebki.model.Car;
import com.ebki.model.CarCheckout;
import com.ebki.request.CheckOut;
import com.ebki.service.CheckoutService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ebik/checkout/**")
@CrossOrigin("*")
public class CheckOutController {

    private final CheckoutService service;

    public CheckOutController(CheckoutService service) {
        this.service = service;
    }

    @PostMapping(
            value = {"/add/{driverID}/{vinNumber}"},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void save(@RequestBody CarCheckout checkout,
                     @PathVariable("driverID") Long driverID,
                     @PathVariable("vinNumber") Long vinNumber) {
        System.out.println("Driver id " + driverID);
        CheckOut requestCheckOut = new CheckOut(checkout);
        service.save(requestCheckOut.getCheckout(), driverID, vinNumber);
    }

    @GetMapping(path = {"/find-by-id/{id}"})
    public Car findCheckOutByID(@PathVariable("id") Long id) {
        return service.findCheckOutByID(id);
    }

    @GetMapping(path = {"/find_checkout_by_driver_id/{id}"})
    public List<Car> findCheckoutByDriverID(@PathVariable("id") Long id) {
        return service.findCheckOutByDriverID(id);
    }

    @GetMapping(value = "/checkout_list")
    public List<Car> getAllCheckOutCar() {
        return service.carCheckOut();
    }

    @GetMapping(path = {"/find_checkout_by_brand/{brand}"})
    public List<Car> findCheckOutByBrand(@PathVariable("brand") String brand) {
        return service.findCheckOutByCarBrand(service.checkoutList(), brand);
    }

    @GetMapping(value = {"/find_checkout_by_year/{year}"})
    public List<Car> findCheckOutByCarYear(@PathVariable("year") int year) {
        return service.findCheckOutByCarYear(service.checkoutList(), year);
    }

    @GetMapping(value = {"/checkout_by_brand_model/{brand}/?{model}"})
    public List<Car> findCheckOutByBrandAndModel(@PathVariable("brand") String brand,
                                                 @PathVariable("model") String model) {
        return service.findCheckOutByCarBrandAndModel(service.checkoutList(), brand, model);
    }

    @DeleteMapping(value = {"/delete/{id}"})
    public void deleteCheckout(@PathVariable("id") Long id) {
        service.deleteCheckOut(id);
    }
}
