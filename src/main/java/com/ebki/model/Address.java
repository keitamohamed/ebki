package com.ebki.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Address")
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    private Long addressID;
    private String street;
    private String city;
    private String state;
    private int zipcode;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "driverID")
    @JsonBackReference
    private Driver driver;
}
