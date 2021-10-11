package com.ebki.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@JsonIgnoreProperties(value = {"addressID"}, allowGetters = true)
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "driverID")
    @JsonBackReference(value = "addressDriver")
    private Driver addressDriver;
}
