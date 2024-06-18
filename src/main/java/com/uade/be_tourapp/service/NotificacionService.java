package com.uade.be_tourapp.service;

import com.uade.be_tourapp.dto.GenericResponseDTO;
import com.uade.be_tourapp.dto.notificacion.NotificacionDTO;
import com.uade.be_tourapp.entity.Notificacion;
import com.uade.be_tourapp.entity.Usuario;
import com.uade.be_tourapp.enums.notificacion.NotificacionStrategyEnum;
import com.uade.be_tourapp.exception.BadRequestException;
import com.uade.be_tourapp.repository.NotificacionRepository;
import com.uade.be_tourapp.strategy.NotificacionStrategy.NotificacionStrategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class NotificacionService {
    private final NotificacionRepository notificacionRepository;
    private final List<NotificacionStrategy> estrategias;
    private final UsuarioService usuarioService;
    private NotificacionStrategy estrategiaNotificacion;

    public NotificacionService(NotificacionRepository notificacionRepository, List<NotificacionStrategy> estrategias, UsuarioService usuarioService) {
        this.notificacionRepository = notificacionRepository;
        this.estrategias = estrategias;
        this.usuarioService = usuarioService;
    }

    public void cambiarEstrategia(NotificacionStrategyEnum nuevaEstrategia) {
        this.estrategiaNotificacion = this.estrategias
                .stream()
                .filter(strategy -> strategy.getIdentification() == nuevaEstrategia)
                .findAny()
                .orElseThrow();
    }

    public void notificar(Notificacion notificacion) {
        notificacionRepository.save(notificacion);
        this.estrategiaNotificacion.notificar(notificacion);
    }

    public List<NotificacionDTO> obtenerNotificaciones() {
        Usuario usuario = usuarioService.obtenerAutenticado();
        List<Notificacion> notificaciones = notificacionRepository.findAllByUsuarioId(usuario.getId());
        return notificaciones
                .stream()
                .map(Notificacion::toDto)
                .toList();
    }

    public GenericResponseDTO leerNotificacion(Long id) {
        Notificacion notificacion = notificacionRepository.findById(id).orElseThrow(() -> new BadRequestException("La notificación no existe"));

        Usuario usuario = usuarioService.obtenerAutenticado();
        if (!Objects.equals(usuario.getId(), notificacion.getUsuario().getId())) throw new BadRequestException("Acceso denegado");

        if (notificacion.getVisto()) throw new BadRequestException("La notificación ya fue leida.");

        notificacion.setVisto(true);
        notificacionRepository.save(notificacion);

        return GenericResponseDTO.builder()
                .message("Notificación marcada como leida.")
                .build();
    }

    public GenericResponseDTO eliminarNotificacion(Long id) {
        Notificacion notificacion = notificacionRepository.findById(id).orElseThrow(() -> new BadRequestException("La notificación no existe"));

        Usuario usuario = usuarioService.obtenerAutenticado();
        if (!Objects.equals(usuario.getId(), notificacion.getUsuario().getId())) throw new BadRequestException("Acceso denegado");

        notificacionRepository.delete(notificacion);

        return GenericResponseDTO.builder()
                .message("Notificación eliminada.")
                .build();
    }
}
