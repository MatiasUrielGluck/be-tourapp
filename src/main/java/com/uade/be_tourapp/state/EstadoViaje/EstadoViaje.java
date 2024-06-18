package com.uade.be_tourapp.state.EstadoViaje;

import com.uade.be_tourapp.entity.Usuario;
import com.uade.be_tourapp.entity.Viaje;
import com.uade.be_tourapp.enums.EstadosViajeEnum;
import com.uade.be_tourapp.exception.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class EstadoViaje {
    private final EstadosViajeEnum nombre;

    public void reservar(Viaje viaje)  {
        throw new BadRequestException("No se puede inicializar el viaje.");
    }

    public void confirmar(Viaje viaje) {
        throw new BadRequestException("No se puede confirmar el viaje.");
    }

    public void cancelar(Viaje viaje, Usuario cancelador) {
        throw new BadRequestException("No se puede cancelar el viaje.");
    }

    public void concluir(Viaje viaje) {
        throw new BadRequestException("No se puede concluir el viaje.");
    }

    abstract public void notificar(Viaje viaje);
}
