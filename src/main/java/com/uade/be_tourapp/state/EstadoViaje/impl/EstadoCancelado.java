package com.uade.be_tourapp.state.EstadoViaje.impl;

import com.uade.be_tourapp.entity.Viaje;
import com.uade.be_tourapp.enums.EstadosViajeEnum;
import com.uade.be_tourapp.state.EstadoViaje.EstadoViaje;
import org.springframework.stereotype.Component;

@Component
public class EstadoCancelado extends EstadoViaje {
    public EstadoCancelado() {
        super(EstadosViajeEnum.CANCELADO);
    }
}
