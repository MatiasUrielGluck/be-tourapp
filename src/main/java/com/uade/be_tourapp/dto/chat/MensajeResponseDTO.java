package com.uade.be_tourapp.dto.chat;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MensajeResponseDTO {
    LocalDateTime fecha;
    String contenido;
    Integer emisorId;
    String emisorNombre;
    Long chatId;
}
