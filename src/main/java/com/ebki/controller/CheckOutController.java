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
            value = {"/checkout"},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void save(@RequestBody CarCheckout checkout) {
        CheckOut requestCheckOut = new CheckOut(checkout);
        service.save(requestCheckOut.getCheckout());
    }

    @GetMapping(path = {"/find-by-id/{id}"})
    public Car findCheckOutByID(@PathVariable("id") Long id) {
        return service.findCheckOutByID(id);
    }

    @GetMapping(value = "/find_all_cars_checkout")
    public List<Car> getAllCheckOutCar() {
        return service.checkoutList();
    }
}
