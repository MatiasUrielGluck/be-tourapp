package com.uade.be_tourapp.enums.notificacion;

import lombok.Getter;

@Getter
public enum MensajesEnum {
    GUIA_READY("Tu credencial fue verificada con éxito.<br>¡Ya podés comenzar a dar servicio!");

    private final String mensaje;

    MensajesEnum(String mensaje) {
        this.mensaje = mensaje;
    }
}
