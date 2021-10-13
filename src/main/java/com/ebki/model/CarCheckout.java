package com.ebki.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@JsonIgnoreProperties(value = {"checkoutID"}, allowGetters = true)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CarCheckout implements Serializable {

    @Id
    @NotNull
    private Long checkoutID;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "driverID")
    @JsonBackReference(value = "checkOutDriver")
    private Driver checkOutDriver;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "carVinNumber")
    @JsonBackReference
    @NotBlank
    private Car car;

    public CarCheckout(Long checkoutID) {
        this.checkoutID = checkoutID;
    }
}
