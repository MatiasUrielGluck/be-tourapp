package com.uade.be_tourapp.state.EstadoViaje.impl;

import com.uade.be_tourapp.entity.Viaje;
import com.uade.be_tourapp.enums.EstadosViajeEnum;
import com.uade.be_tourapp.exception.BadRequestException;
import com.uade.be_tourapp.state.EstadoViaje.EstadoViaje;

public class EstadoCancelado extends EstadoViaje {
    public EstadoCancelado() {
        super(EstadosViajeEnum.CANCELADO);
    }

    public void confirmar(Viaje viaje) {
        throw new BadRequestException("El viaje está cancelado y no puede ser confirmado.");
    }

    public void cancelar(Viaje viaje) {
        throw new BadRequestException("El estado ya se encuentra cancelado.");
    }

    public void concluir(Viaje viaje) {
        throw new BadRequestException("No se puede concluir un viaje cancelado.");
    }

    public void notificar(Viaje viaje) {

    }
}
