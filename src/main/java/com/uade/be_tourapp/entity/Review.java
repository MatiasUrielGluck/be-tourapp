package com.uade.be_tourapp.entity;

import com.uade.be_tourapp.dto.review.ReviewResponseDTO;
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
    private Double puntuacion;

    @OneToOne
    @JoinColumn(name = "viaje_id", referencedColumnName = "id")
    private Viaje viaje;

    public ReviewResponseDTO toDto() {
        return ReviewResponseDTO.builder()
                .id(id)
                .viajeId(viaje.getId())
                .comentario(comentario)
                .puntuacion(puntuacion)
                .pais(viaje.getServicio().getPais())
                .ciudad(viaje.getServicio().getCiudad())
                .build();
    }
}
