package com.uade.be_tourapp.state.EstadoViaje.impl;

import com.uade.be_tourapp.entity.Guia;
import com.uade.be_tourapp.entity.Notificacion;
import com.uade.be_tourapp.entity.Usuario;
import com.uade.be_tourapp.entity.Viaje;
import com.uade.be_tourapp.enums.EstadosViajeEnum;
import com.uade.be_tourapp.enums.notificacion.AccionEnum;
import com.uade.be_tourapp.enums.notificacion.NotificacionStrategyEnum;
import com.uade.be_tourapp.service.ChatService;
import com.uade.be_tourapp.service.NotificacionService;
import com.uade.be_tourapp.service.TransaccionService;
import com.uade.be_tourapp.state.EstadoViaje.EstadoViaje;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EstadoReservado extends EstadoViaje {
    private final TransaccionService transaccionService;
    private final NotificacionService notificacionService;
    private final ChatService chatService;

    public EstadoReservado(TransaccionService transaccionService, NotificacionService notificacionService, ChatService chatService) {
        super(EstadosViajeEnum.RESERVADO);
        this.transaccionService = transaccionService;
        this.notificacionService = notificacionService;
        this.chatService = chatService;
    }

    @Override
    public void confirmar(Viaje viaje) {
        // Crear chat entre los usuarios
        chatService.crearChat(viaje.getTurista(), viaje.getGuia());

        // Generar la push notification
        Notificacion notificacion = Notificacion.builder()
                .usuario(viaje.getTurista())
                .mensaje("¡El viaje ha sido confirmado!")
                .fecha(LocalDateTime.now())
                .visto(false)
                .build();
        notificacionService.cambiarEstrategia(NotificacionStrategyEnum.PUSH);
        notificacionService.notificar(notificacion);

        viaje.cambiarEstado(EstadosViajeEnum.CONFIRMADO);
    }

    @Override
    public void cancelar(Viaje viaje, Usuario cancelador) {
        // Generar la push notification
        Notificacion notificacion = Notificacion.builder()
                .fecha(LocalDateTime.now())
                .visto(false)
                .build();

        if (cancelador.getClass() == Guia.class) {
            transaccionService.generarDevolucion(viaje);

            notificacion.setUsuario(viaje.getTurista());
            notificacion.setMensaje(cancelador.getNombre() + " canceló la reserva.<br>Presioná para ver la devolución del dinero.");
            notificacion.setAccion(AccionEnum.VER_DEVOLUCION);
        } else {
            notificacion.setUsuario(viaje.getGuia());
            notificacion.setMensaje(cancelador.getNombre() + " canceló la reserva.");
        }

        notificacionService.cambiarEstrategia(NotificacionStrategyEnum.PUSH);
        notificacionService.notificar(notificacion);

        viaje.cambiarEstado(EstadosViajeEnum.CANCELADO);
    }
}
