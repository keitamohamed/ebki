package com.ebki.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

//@MappedSuperclass
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "CAR_TYPE")
@Entity
@JsonIgnoreProperties(value = {"carVinNumber"}, allowGetters = true)
@Getter @Setter @NoArgsConstructor
public class Car implements Comparable<Integer> {

    @Id
    @NotNull
    private Long vin;
    @Column(nullable = false)
    @NotBlank(message = "Brand name cannot be null or empty")
    private String brand;
    @Column(nullable = false)
    @NotBlank(message = "Model cannot be null or empty")
    private String model;
    @Column(nullable = false)
    @NotBlank(message = "Body style cannot be null or empty")
    private String bodyType;
    @Column(nullable = false)
    @NotNull
    @Min(1985)
    private int year;

    @OneToOne(mappedBy = "carCheckOut", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "carCheckOut")
    private CarCheckout checkout;
    @OneToOne(mappedBy = "car", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JsonManagedReference
    private CarCheckIn checkIn;

    public Car(Long carVinNumber, String brand, String model, String bodyStyle, int modelYear) {
        this.vin = carVinNumber;
        this.brand = brand;
        this.model = model;
        this.bodyType = bodyStyle;
        this.year = modelYear;
    }

    @Override
    public int compareTo(Integer o) {
        return o.compareTo(this.year);
    }
}
