package com.uade.be_tourapp.state.EstadoViaje.impl;

import com.uade.be_tourapp.entity.Viaje;
import com.uade.be_tourapp.enums.EstadosViajeEnum;
import com.uade.be_tourapp.state.EstadoViaje.EstadoViaje;
import org.springframework.stereotype.Component;

@Component
public class EstadoReservado extends EstadoViaje {
    public EstadoReservado() {
        super(EstadosViajeEnum.RESERVADO);
    }

    @Override
    public void confirmar(Viaje viaje) {
        viaje.cambiarEstado(EstadosViajeEnum.CONFIRMADO);
    }

    @Override
    public void cancelar(Viaje viaje) {
        viaje.cambiarEstado(EstadosViajeEnum.CANCELADO);
    }

    public void notificar(Viaje viaje) {}
}
