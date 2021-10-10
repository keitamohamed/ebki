package com.ebki.request;

import com.ebki.model.CarCheckIn;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CheckIn {

    private final CarCheckIn checkIn;

    public CheckIn(@JsonProperty("carCheckIn") CarCheckIn checkIn) {
        this.checkIn = checkIn;
    }

    public CarCheckIn getCheckIn() {
        return this.checkIn;
    }
}
