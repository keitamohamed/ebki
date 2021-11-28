package com.ebki.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@JsonIgnoreProperties(value = {"authID"}, allowGetters = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Authenticate {

    @Id
    @NotNull
    private Long authID;
    private String username;
    private String password;
    private boolean isAccountNonExpired;
    private boolean isAccountNotLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;
    @OneToOne(mappedBy = "authenticate", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Role role;
}
