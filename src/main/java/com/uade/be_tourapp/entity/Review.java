package com.uade.be_tourapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "review")
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "puntuacion")
    private Integer puntuacion;

    @OneToOne
    @JoinColumn(name = "viaje_id", referencedColumnName = "id")
    private Viaje viaje;
}
