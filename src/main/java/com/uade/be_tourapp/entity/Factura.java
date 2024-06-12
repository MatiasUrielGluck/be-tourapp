package com.uade.be_tourapp.entity;

import com.uade.be_tourapp.enums.DocumentoEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
@Entity
@DiscriminatorValue("FACTURA")
public class Factura extends Documento {
    @Transient
    private final double porcentajeComision = 0.10;
    @Transient
    private final double porcentajeReserva = 0.30;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "comision")
    private Double comision;

    @Column(name = "motivo")
    @Enumerated(EnumType.STRING)
    private DocumentoEnum motivo;

    @Column(name = "pagada")
    private Boolean pagada;

    public void configurar() {
        setPrecio(calcularPrecio());
        setComision(calcularComision());
        setTotal(calcularTotal());
        setPagada(false);
    }

    public Double calcularPrecio() {
        return getViaje().getServicio().getPrecio();
    }

    public Double calcularComision() {
        if (getMotivo() == DocumentoEnum.ANTICIPO) return 0.0;
        return getPrecio() * porcentajeComision;
    }

    public Double calcularReserva() {
        return getViaje().getServicio().getPrecio() * porcentajeReserva;
    }

    public Double calcularTotal() {
        if (getMotivo() == DocumentoEnum.ANTICIPO) return calcularReserva();
        return getPrecio() - calcularReserva() + calcularComision();
    }
}
