package com.ebki.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@JsonIgnoreProperties(value = {"checkInID"}, allowGetters = true)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CarCheckIn implements Serializable {

    @Id
    private Long checkInID;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "driverID")
    @JsonBackReference(value = "carCheckIn")
    private Driver carCheckIn;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "carVinNumber")
    @JsonBackReference
    private Car car;

    public CarCheckIn(Long checkInID) {
        this.checkInID = checkInID;
    }

    public int compareToYear(int modelYear) {
        return this.getCar().getYear() - modelYear;
    }

}
