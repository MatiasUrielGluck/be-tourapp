package com.uade.be_tourapp.enums;

import com.uade.be_tourapp.state.EstadoViaje.EstadoViaje;
import com.uade.be_tourapp.state.EstadoViaje.impl.EstadoCancelado;
import com.uade.be_tourapp.state.EstadoViaje.impl.EstadoConfirmado;
import com.uade.be_tourapp.state.EstadoViaje.impl.EstadoReservado;
import lombok.Getter;

@Getter
public enum EstadosViajeEnum {
    RESERVADO(new EstadoReservado()),
    CONFIRMADO(new EstadoConfirmado()),
    CANCELADO(new EstadoCancelado());

    private final EstadoViaje estadoViaje;

    EstadosViajeEnum(EstadoViaje estadoViaje) {
        this.estadoViaje = estadoViaje;
    }
}
