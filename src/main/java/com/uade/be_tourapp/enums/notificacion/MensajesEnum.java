package com.uade.be_tourapp.enums.notificacion;

import lombok.Getter;

@Getter
public enum MensajesEnum {
    GUIA_READY("Tu credencial fue verificada con éxito.<br>¡Ya podés comenzar a dar servicio!"),

    TROFEO_EXITO("¡Felicitaciones!<br>¡Ganaste el trofeo al éxito!"),

    TROFEO_REVIEW("¡Felicitaciones!<br>¡Ganaste el trofeo a la reseña!");

    private final String mensaje;

    MensajesEnum(String mensaje) {
        this.mensaje = mensaje;
    }
}
