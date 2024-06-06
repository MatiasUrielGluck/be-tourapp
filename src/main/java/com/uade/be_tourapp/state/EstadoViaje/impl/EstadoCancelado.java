package com.uade.be_tourapp.state.EstadoViaje.impl;

import com.uade.be_tourapp.entity.Viaje;
import com.uade.be_tourapp.exception.BadRequestException;
import com.uade.be_tourapp.state.EstadoViaje.EstadoViaje;
import org.springframework.stereotype.Component;

@Component
public class EstadoCancelado implements EstadoViaje {
    @Override
    public void confirmar(Viaje viaje) {
        throw new BadRequestException("El viaje está cancelado y no puede ser confirmado.");
    }

    @Override
    public void cancelar(Viaje viaje) {
        throw new BadRequestException("El estado ya se encuentra cancelado.");
    }

    @Override
    public void notificar(Viaje viaje) {

    }
}
