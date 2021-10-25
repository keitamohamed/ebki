package com.ebki.controller;

import com.ebki.model.CarCheckIn;
import com.ebki.service.CheckInService;
import com.ebki.service.CheckoutService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ebik/check_in/**")
@CrossOrigin("*")
public class CheckInController {

    private final CheckInService service;
    private final CheckoutService checkoutService;

    public CheckInController(CheckInService service, CheckoutService checkoutService) {
        this.service = service;
        this.checkoutService = checkoutService;
    }

    @PostMapping(value = {"/new_checkin/{driver_id}/{checkout_id}"},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addNewCheckIn(@RequestBody CarCheckIn carCheckIn,
                              @PathVariable("driver_id") Long driverID,
                              @PathVariable("checkout_id") Long checkoutID) {
        service.save(carCheckIn, driverID, checkoutID);
        checkoutService.deleteCheckOut(checkoutID);
    }
}
