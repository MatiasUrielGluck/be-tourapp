package com.uade.be_tourapp.state.EstadoViaje.impl;

import com.uade.be_tourapp.entity.Viaje;
import com.uade.be_tourapp.enums.EstadosViajeEnum;
import com.uade.be_tourapp.exception.BadRequestException;
import com.uade.be_tourapp.state.EstadoViaje.EstadoViaje;

public class EstadoReservado extends EstadoViaje {
    public EstadoReservado() {
        super(EstadosViajeEnum.RESERVADO);
    }

    public void confirmar(Viaje viaje) {
        viaje.cambiarEstado(new EstadoConfirmado());
    }

    public void cancelar(Viaje viaje) {
        viaje.cambiarEstado(new EstadoCancelado());
    }

    public void concluir(Viaje viaje) {
        throw new BadRequestException("Primero se debe confirmar el viaje.");
    }

    public void notificar(Viaje viaje) {

    }
}
