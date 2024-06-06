package com.uade.be_tourapp.state.EstadoViaje;

import com.uade.be_tourapp.entity.Viaje;

public interface EstadoViaje {
    void confirmar(Viaje viaje);
    void cancelar(Viaje viaje);
    void notificar(Viaje viaje);
}
