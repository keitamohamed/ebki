package com.ebki.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
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
    @Column(nullable = false)
    private String gender;
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "US/NEW YORK")
    private Date dob;
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

    @OneToOne(mappedBy = "authDriver", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "authDriver")
    private Authenticate authenticate;


    @OneToMany(mappedBy = "driverAddress", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "driverAddress")
    private Set<Address> address;

    @OneToMany(mappedBy = "checkoutCar", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "checkoutCar")
    private Set<CarCheckout> checkout;

    @OneToMany(mappedBy = "carCheckIn", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "carCheckIn")
    private Set<CarCheckIn> carCheckIns;
}
