package com.ebki;

import com.ebki.model.Car;
import com.ebki.model.CarCheckIn;
import com.ebki.util.Util;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class FakeData {

    private List<Car> carList;
    private List<CarCheckIn> carCheckInList;

    public FakeData() {
        carList = new ArrayList<>();
        carCheckInList = new ArrayList<>();
    }

    public FakeData(List<Car> car, List<CarCheckIn> checkIn) {
        this.carList = car;
        this.carCheckInList = checkIn;
    }

    public List<Car> carList() {
        this.carList.add(new Car(635521L, "Ford", "Crown Victoria", "Sedan", 2007));
        carList.add(new Car(537261L, "Lexus", "GX", "Convertible", 2021));
        carList.add(new Car(737291L, "Nissan", "Altima", "Sedan", 2009));
        carList.add(new Car(462821L, "Toyota", "Tacoma", "SUV", 2017));
        carList.add(new Car(5478291L, "Toyota", "Matrix", "SUV", 2007));
        return this.carList;
    }

    public List<CarCheckIn> carCheckInList() {
        carCheckInList.add(new CarCheckIn(Util.generateID(797367281)));
        carCheckInList.add(new CarCheckIn(Util.generateID(797367281)));
        carCheckInList.add(new CarCheckIn(Util.generateID(797367281)));
        carCheckInList.add(new CarCheckIn(Util.generateID(797367281)));
        carCheckInList.add(new CarCheckIn(Util.generateID(797367281)));
        return this.carCheckInList;
    }
}
