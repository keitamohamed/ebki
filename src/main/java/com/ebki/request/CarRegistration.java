package com.ebki.request;

import com.ebki.model.Car;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CarRegistration {

    private final Car car;

    public CarRegistration(@JsonProperty("car") Car car) {
        this.car = car;
    }

    public Car getCar() {
        return this.car;
    }
}
