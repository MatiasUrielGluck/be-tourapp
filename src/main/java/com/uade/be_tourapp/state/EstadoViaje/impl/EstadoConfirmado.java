package com.uade.be_tourapp.state.EstadoViaje.impl;

import com.uade.be_tourapp.entity.Viaje;
import com.uade.be_tourapp.enums.DocumentoEnum;
import com.uade.be_tourapp.enums.EstadosViajeEnum;
import com.uade.be_tourapp.service.TransaccionService;
import com.uade.be_tourapp.state.EstadoViaje.EstadoViaje;
import org.springframework.stereotype.Component;

@Component
public class EstadoConfirmado extends EstadoViaje {
    private final TransaccionService transaccionService;

    public EstadoConfirmado(TransaccionService transaccionService) {
        super(EstadosViajeEnum.CONFIRMADO);
        this.transaccionService = transaccionService;
    }

    @Override
    public void cancelar(Viaje viaje) {
        viaje.cambiarEstado(EstadosViajeEnum.CANCELADO);
    }

    @Override
    public void concluir(Viaje viaje) {
        transaccionService.generarFactura(viaje, DocumentoEnum.FINAL);
        viaje.cambiarEstado(EstadosViajeEnum.CONCLUIDO);
    }

    public void notificar(Viaje viaje) {}
}
