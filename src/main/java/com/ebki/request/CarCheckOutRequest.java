package com.ebki.request;

import com.ebki.model.CarCheckout;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CarCheckOutRequest {

    private final CarCheckout checkout;

    public CarCheckOutRequest(@JsonProperty("checkout") CarCheckout checkout) {
        this.checkout = checkout;
    }

    public CarCheckout getCheckout() {
        return this.checkout;
    }
}
