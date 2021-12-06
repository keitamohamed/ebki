package com.ebki.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ImageFile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String carImageLink;

    @OneToOne
    @JoinColumn(name = "vin")
    @JsonBackReference(value = "carImg")
    private Car carImg;

    @Override
    public int hashCode() {
        return Objects.hash(id, carImageLink, carImg);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ImageFile that = (ImageFile) obj;
        return Objects.equals(id, that.id)
                && Objects.equals(carImageLink, that.carImageLink)
                && Objects.equals(carImg, that.carImg);
    }
}
