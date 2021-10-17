package com.ebki;

import com.ebki.model.Car;
import com.ebki.model.CarCheckIn;
import com.ebki.model.CarCheckout;
import com.ebki.util.Util;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class TestData {

    private List<Car> carList;
    private List<CarCheckIn> carCheckInList;
    private List<CarCheckout> carCheckoutList;
    private final int BOUND_VALUE = 999999999;

    public TestData() {
        carList = new ArrayList<>();
        carCheckInList = new ArrayList<>();
        carCheckoutList = new ArrayList<>();

    }

    public TestData(List<Car> car, List<CarCheckIn> checkIn) {
        this.carList = car;
        this.carCheckInList = checkIn;
    }

    public List<Car> carList() {
        this.carList.add(new Car(635521L, "Ford", "Crown Victoria", "Sedan", 2012));
        carList.add(new Car(537261L, "Lexus", "GX", "Convertible", 2021));
        carList.add(new Car(737291L, "Nissan", "Altima", "Sedan", 2016));
        carList.add(new Car(462821L, "Toyota", "Tacoma", "SUV", 2017));
        carList.add(new Car(5478291L, "Toyota", "Matrix", "SUV", 2016));
        return this.carList;
    }

    public List<CarCheckIn> carCheckInList() {
        carCheckInList.add(new CarCheckIn(Util.generateID(getBOUND_VALUE())));
        carCheckInList.add(new CarCheckIn(Util.generateID(getBOUND_VALUE())));
        carCheckInList.add(new CarCheckIn(Util.generateID(getBOUND_VALUE())));
        carCheckInList.add(new CarCheckIn(Util.generateID(getBOUND_VALUE())));
        carCheckInList.add(new CarCheckIn(Util.generateID(getBOUND_VALUE())));
        return this.carCheckInList;
    }

    public List<CarCheckout> carCheckoutList() {
        carCheckoutList.add(new CarCheckout(Util.generateID(getBOUND_VALUE())));
        carCheckoutList.add(new CarCheckout(Util.generateID(getBOUND_VALUE())));
        carCheckoutList.add(new CarCheckout(Util.generateID(getBOUND_VALUE())));
        carCheckoutList.add(new CarCheckout(Util.generateID(getBOUND_VALUE())));
        carCheckoutList.add(new CarCheckout(Util.generateID(getBOUND_VALUE())));
        return this.carCheckoutList;
    }
}
