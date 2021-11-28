package com.ebki.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@JsonIgnoreProperties(value = {"roleID"}, allowGetters = true)

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @NotNull
    private Long roleID;
    private String roleType;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "authID")
    @JsonBackReference
    private Authenticate authenticate;
}
