package com.uade.be_tourapp.dto.notificacion;

import com.uade.be_tourapp.enums.notificacion.AccionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class NotificacionDTO {
    private Long id;
    private String mensaje;
    private AccionEnum accion;
    private LocalDateTime fecha;
    private Boolean visto;
}
