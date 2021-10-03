package com.ebki.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CarCheckout")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CarCheckout implements Serializable {

    @Id
    private Long checkoutID;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "driverID")
    @JsonBackReference
    private Driver driverCheckOut;
}
