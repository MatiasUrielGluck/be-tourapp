package com.uade.be_tourapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@Entity
@DiscriminatorValue("DEVOLUCION")
public class Devolucion extends Documento {
    @Transient
    private Double porcentajeDevolucion = 1.0;

    @Transient
    private Double devolucionAdicional = 0.0;

    @Transient
    private Double montoCancelable;

    public void totalizar() {
        super.setTotal(montoCancelable);
    }

    @Override
    public Double calcularTotal() {
        return montoCancelable * porcentajeDevolucion + devolucionAdicional;
    }
}
