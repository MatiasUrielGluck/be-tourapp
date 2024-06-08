package com.uade.be_tourapp.state.EstadoViaje.impl;

import com.uade.be_tourapp.entity.Viaje;
import com.uade.be_tourapp.enums.EstadosViajeEnum;
import com.uade.be_tourapp.exception.BadRequestException;
import com.uade.be_tourapp.state.EstadoViaje.EstadoViaje;

public class EstadoConfirmado extends EstadoViaje {
    public EstadoConfirmado() {
        super(EstadosViajeEnum.CONFIRMADO);
    }

    public void confirmar(Viaje viaje) {
        throw new BadRequestException("El estado ya se encuentra confirmado.");
    }

    public void cancelar(Viaje viaje) {
        viaje.cambiarEstado(new EstadoCancelado());
    }

    public void notificar(Viaje viaje) {

    }
}
