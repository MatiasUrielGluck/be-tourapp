package com.uade.be_tourapp.state.EstadoViaje.impl;

import com.uade.be_tourapp.entity.Guia;
import com.uade.be_tourapp.entity.Usuario;
import com.uade.be_tourapp.entity.Viaje;
import com.uade.be_tourapp.enums.EstadosViajeEnum;
import com.uade.be_tourapp.service.TransaccionService;
import com.uade.be_tourapp.state.EstadoViaje.EstadoViaje;
import org.springframework.stereotype.Component;

@Component
public class EstadoReservado extends EstadoViaje {
    private final TransaccionService transaccionService;

    public EstadoReservado(TransaccionService transaccionService) {
        super(EstadosViajeEnum.RESERVADO);
        this.transaccionService = transaccionService;
    }

    @Override
    public void confirmar(Viaje viaje) {
        viaje.cambiarEstado(EstadosViajeEnum.CONFIRMADO);
    }

    @Override
    public void cancelar(Viaje viaje, Usuario cancelador) {
        if (cancelador.getClass() == Guia.class) {
            transaccionService.generarDevolucion(viaje);
        }

        viaje.cambiarEstado(EstadosViajeEnum.CANCELADO);
    }
}
