package com.uade.be_tourapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Entity
@Table(name = "viaje")
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder



public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "turista_id", referencedColumnName = "id")
    private Usuario turista;

    @ManyToOne
    @JoinColumn(name = "guia_id", referencedColumnName = "id")
    private Guia guia;

    @Temporal(TemporalType.DATE)
    @Column(name = "fechaInicio")
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Column(name = "fechaFin")
    private Date fechaFin;

    @Column(name = "precio")
    private double precio;

}
