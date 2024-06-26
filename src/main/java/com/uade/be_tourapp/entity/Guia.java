package com.uade.be_tourapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
@SuperBuilder
@DiscriminatorValue("GUIA")
public class Guia extends Usuario {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credencial_numero", referencedColumnName = "numero")
    private Credencial credencial;

    @OneToMany(mappedBy = "guia")
    private List<Servicio> servicios;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
            @JoinTable(
                    name = "idioma_guia",
                    joinColumns = @JoinColumn(name = "guia_id"),
                    inverseJoinColumns = @JoinColumn(name = "idioma_id")
            )
    private List<Idioma> idiomas;
}
