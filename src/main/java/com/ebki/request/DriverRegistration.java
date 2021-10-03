package com.ebki.request;

import com.ebki.model.Driver;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DriverRegistration {

    private final Driver driver;

    public DriverRegistration(@JsonProperty("driver") Driver driver) {
        this.driver = driver;
    }

    public Driver getDriver() {
        return driver;
    }
}
