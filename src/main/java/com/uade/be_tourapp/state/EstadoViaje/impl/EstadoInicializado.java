package com.uade.be_tourapp.state.EstadoViaje.impl;

import com.uade.be_tourapp.entity.Notificacion;
import com.uade.be_tourapp.entity.Viaje;
import com.uade.be_tourapp.enums.DocumentoEnum;
import com.uade.be_tourapp.enums.EstadosViajeEnum;
import com.uade.be_tourapp.enums.notificacion.NotificacionStrategyEnum;
import com.uade.be_tourapp.service.NotificacionService;
import com.uade.be_tourapp.service.TransaccionService;
import com.uade.be_tourapp.state.EstadoViaje.EstadoViaje;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EstadoInicializado extends EstadoViaje {
    private final TransaccionService transaccionService;
    private final NotificacionService notificacionService;

    public EstadoInicializado(TransaccionService transaccionService, NotificacionService notificacionService) {
        super(EstadosViajeEnum.INICIALIZADO);
        this.transaccionService = transaccionService;
        this.notificacionService = notificacionService;
    }

    @Override
    public void reservar(Viaje viaje)  {
        // Generar la factura de anticipo
        transaccionService.generarFactura(viaje, DocumentoEnum.ANTICIPO);

        // Generar la push notification
        Notificacion notificacion = Notificacion.builder()
                .usuario(viaje.getGuia())
                .mensaje("¡" + viaje.getTurista().getNombre() + " te acaba de reservar un servicio!<br>Presioná para ver más información.")
                .fecha(LocalDateTime.now())
                .visto(false)
                .build();
        notificacionService.cambiarEstrategia(NotificacionStrategyEnum.PUSH);
        notificacionService.notificar(notificacion);

        viaje.cambiarEstado(EstadosViajeEnum.RESERVADO);
    }

    public void notificar(Viaje viaje) {}
}
