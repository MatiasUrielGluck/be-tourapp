package com.uade.be_tourapp.state.EstadoViaje.impl;

import com.uade.be_tourapp.entity.Viaje;
import com.uade.be_tourapp.enums.EstadosViajeEnum;
import com.uade.be_tourapp.exception.BadRequestException;
import com.uade.be_tourapp.state.EstadoViaje.EstadoViaje;
import org.springframework.stereotype.Component;

@Component
public class EstadoConfirmado implements EstadoViaje {
    @Override
    public void confirmar(Viaje viaje) {
        throw new BadRequestException("El estado ya se encuentra confirmado.");
    }

    @Override
    public void cancelar(Viaje viaje) {
        viaje.setEstado(EstadosViajeEnum.CANCELADO);
    }

    @Override
    public void notificar(Viaje viaje) {

    }
}
