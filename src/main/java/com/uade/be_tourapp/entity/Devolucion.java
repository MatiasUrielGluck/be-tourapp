package com.uade.be_tourapp.entity;

import com.uade.be_tourapp.enums.EstadosViajeEnum;
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
    private double porcentaje = 0.30;

    public Double calcularTotal() {
        if (getViaje().getEstado().getNombre() == EstadosViajeEnum.RESERVADO) {
            return 0.0; // TODO: acá hay que devolver el total de la factura de anticipo asociada
        }

        return getViaje().getServicio().getPrecio() * porcentaje;
    }
}
