package com.ebki.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@JsonIgnoreProperties(value = {"driverID"}, allowGetters = true)
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
public class Driver {

    @Id
    @NotNull
    private Long driverID;
    @NotBlank
    @Column(nullable = false)
    private String firstName;
    @NotBlank
    @Column(nullable = false)
    private String lastName;
    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    private String email;
    @NotBlank
    @Column(nullable = false)
    private String phoneNum;

    public Driver(Long driverID, String firstName, String lastName, String email, String phoneNum) {
        this.driverID = driverID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNum = phoneNum;
    }

    @OneToMany(mappedBy = "driverAddress", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "driverAddress")
    private Set<Address> address;

    @OneToMany(mappedBy = "checkOutDriver", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "checkOutDriver")
    private Set<CarCheckout> checkout;

    @OneToMany(mappedBy = "checkInDriver", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "checkInDriver")
    private Set<CarCheckIn> carCheckIns;
}
