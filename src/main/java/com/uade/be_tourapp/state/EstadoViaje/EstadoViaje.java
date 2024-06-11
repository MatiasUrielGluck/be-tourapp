package com.uade.be_tourapp.state.EstadoViaje;

import com.uade.be_tourapp.entity.Viaje;
import com.uade.be_tourapp.enums.EstadosViajeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class EstadoViaje {
    private final EstadosViajeEnum nombre;

    abstract public void confirmar(Viaje viaje);
    abstract public void cancelar(Viaje viaje);
    abstract public void concluir(Viaje viaje);
    abstract public void notificar(Viaje viaje);
}
