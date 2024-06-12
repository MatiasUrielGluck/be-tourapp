package com.uade.be_tourapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "documento")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo",
        discriminatorType = DiscriminatorType.STRING)

public class Documento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "total")
    private Double total;

    @ManyToOne
    @JoinColumn(name = "viaje_id", referencedColumnName = "id")
    private Viaje viaje;
}
