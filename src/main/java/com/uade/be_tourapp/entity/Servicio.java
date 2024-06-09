package com.uade.be_tourapp.entity;

import com.uade.be_tourapp.enums.TipoServicioEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "servicio")
@Builder
@Data
@AllArgsConstructor @NoArgsConstructor
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private TipoServicioEnum tipo;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "pais")
    private String pais;

    @Column(name = "ciudad")
    private String ciudad;

    @ManyToOne
    @JoinColumn(name = "guia_id", referencedColumnName = "id")
    private Guia guia;
}
