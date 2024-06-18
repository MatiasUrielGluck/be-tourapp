package com.uade.be_tourapp.entity;

import com.uade.be_tourapp.dto.notificacion.NotificacionDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificacion")
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mensaje")
    private String mensaje;

    @Column(name = "visto")
    private Boolean visto;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha")
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    public NotificacionDTO toDto() {
        return NotificacionDTO.builder()
                .id(this.id)
                .mensaje(this.mensaje)
                .fecha(this.fecha)
                .visto(this.visto)
                .build();
    }
}
