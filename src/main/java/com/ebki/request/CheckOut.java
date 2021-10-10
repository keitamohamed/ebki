package com.ebki.request;

import com.ebki.model.CarCheckout;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CheckOut {

    private final CarCheckout checkout;

    public CheckOut(@JsonProperty("checkout") CarCheckout checkout) {
        this.checkout = checkout;
    }

    public CarCheckout getCheckout() {
        return this.checkout;
    }
}
