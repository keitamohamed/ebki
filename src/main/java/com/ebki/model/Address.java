package com.ebki.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@JsonIgnoreProperties(value = {"addressID"}, allowGetters = true)
@Getter @Setter @NoArgsConstructor
public class Address {

    @Id
    private Long addressID;
    @NotBlank
    private String street;
    @NotBlank
    private String city;
    @NotBlank
    private String state;
    @NotNull
    @Digits(integer = 5,  message = "zipcode cannot be greater than 5 digit number", fraction = 0)
    private int zipcode;

    public Address(Long addressID, String street, String city, String state, int zipcode, Driver addressDriver) {
        this.addressID = addressID;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.addressDriver = addressDriver;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "driverID")
    @JsonBackReference(value = "addressDriver")
    private Driver addressDriver;
}
