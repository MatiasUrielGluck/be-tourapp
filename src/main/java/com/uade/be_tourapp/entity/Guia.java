package com.uade.be_tourapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@DiscriminatorValue("GUIA")
public class Guia extends Usuario {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credencial_numero", referencedColumnName = "numero")
    private Credencial credencial;
}
