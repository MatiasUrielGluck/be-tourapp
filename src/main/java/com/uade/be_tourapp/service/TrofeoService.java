package com.uade.be_tourapp.service;

import com.uade.be_tourapp.entity.Guia;
import com.uade.be_tourapp.entity.Notificacion;
import com.uade.be_tourapp.entity.Trofeo;
import com.uade.be_tourapp.entity.Usuario;
import com.uade.be_tourapp.enums.notificacion.MensajesEnum;
import com.uade.be_tourapp.enums.notificacion.NotificacionStrategyEnum;
import com.uade.be_tourapp.enums.trofeo.TrofeoEnum;
import com.uade.be_tourapp.repository.TrofeoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TrofeoService {
    private final TrofeoRepository trofeoRepository;
    private final NotificacionService notificacionService;

    public TrofeoService(TrofeoRepository trofeoRepository, NotificacionService notificacionService) {
        this.trofeoRepository = trofeoRepository;
        this.notificacionService = notificacionService;
    }

    public void comprobarTrofeosGuia(Guia usuario, Double puntuacion, Integer cantReviews) {
        if (cantReviews > 10 && puntuacion >= 4.5) {
            asignarTrofeo(usuario, TrofeoEnum.EXITO);
        }
    }

    public void comprobarTrofeosTurista(Usuario usuario, Integer cantReviews) {
        if (cantReviews > 10) {
            asignarTrofeo(usuario, TrofeoEnum.REVIEW);
        }
    }

    public void asignarTrofeo(Usuario usuario, TrofeoEnum tipo) {
        if (trofeoRepository.existsByUsuarioIdAndTipo(usuario.getId(), tipo)) return;

        Trofeo trofeo = Trofeo.builder()
                .tipo(tipo)
                .usuario(usuario)
                .build();
        trofeoRepository.save(trofeo);

        // Generar la push notification
        Notificacion notificacion = Notificacion.builder()
                .usuario(usuario)
                .mensaje(tipo == TrofeoEnum.EXITO ? MensajesEnum.TROFEO_EXITO.getMensaje() : MensajesEnum.TROFEO_REVIEW.getMensaje())
                .fecha(LocalDateTime.now())
                .visto(false)
                .build();
        notificacionService.cambiarEstrategia(NotificacionStrategyEnum.PUSH);
        notificacionService.notificar(notificacion);
    }
}
