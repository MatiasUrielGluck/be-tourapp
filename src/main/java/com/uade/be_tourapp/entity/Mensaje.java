package com.uade.be_tourapp.entity;

import com.uade.be_tourapp.dto.chat.MensajeResponseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "mensaje")
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Mensaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contenido")
    private String contenido;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha")
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "emisor_id", referencedColumnName = "id")
    private Usuario emisor;

    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    private Chat chat;

    public MensajeResponseDTO toDto() {
        return MensajeResponseDTO.builder()
                .chatId(this.chat.getId())
                .contenido(this.contenido)
                .fecha(this.fecha)
                .emisorId(this.emisor.getId())
                .emisorNombre(this.emisor.getNombre())
                .build();
    }
}
