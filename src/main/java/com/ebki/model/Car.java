package com.ebki.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

//@MappedSuperclass
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "CAR_TYPE")
@Entity
@JsonIgnoreProperties(value = {"carVinNumber"}, allowGetters = true)
@Table(name = "car")
@Getter @Setter @NoArgsConstructor
public class Car {

    @Id
    @NotNull
    private Long carVinNumber;
    @Column(nullable = false)
    @NotBlank
    private String brand;
    @Column(nullable = false)
    @NotBlank
    private String model;
    @Column(nullable = false)
    @NotBlank
    private String bodyStyle;
    @Column(nullable = false)
    @NotBlank
    private int year;

    @OneToOne(mappedBy = "car", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JsonManagedReference
    private CarCheckIn checkIn;

    @OneToOne(mappedBy = "car", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JsonManagedReference
    private CarCheckout checkout;

    public Car(Long carVinNumber, String brand, String model, String bodyStyle, int modelYear) {
        this.carVinNumber = carVinNumber;
        this.brand = brand;
        this.model = model;
        this.bodyStyle = bodyStyle;
        this.year = modelYear;
    }

}
