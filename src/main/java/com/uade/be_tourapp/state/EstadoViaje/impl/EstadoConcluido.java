package com.uade.be_tourapp.state.EstadoViaje.impl;

import com.uade.be_tourapp.entity.Viaje;
import com.uade.be_tourapp.enums.EstadosViajeEnum;
import com.uade.be_tourapp.exception.BadRequestException;
import com.uade.be_tourapp.state.EstadoViaje.EstadoViaje;
import org.springframework.stereotype.Component;

@Component
public class EstadoConcluido extends EstadoViaje {
    public EstadoConcluido() {
        super(EstadosViajeEnum.CONCLUIDO);
    }

    public void confirmar(Viaje viaje) {
        throw new BadRequestException("El viaje ya está concluido.");
    }

    public void cancelar(Viaje viaje) {
        throw new BadRequestException("El viaje ya está concluido.");
    }

    public void concluir(Viaje viaje) {
        throw new BadRequestException("El viaje ya está concluido.");
    }

    public void notificar(Viaje viaje) {

    }
}
