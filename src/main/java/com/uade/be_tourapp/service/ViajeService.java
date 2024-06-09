package com.uade.be_tourapp.service;

import com.uade.be_tourapp.dto.ViajeRequestDTO;
import com.uade.be_tourapp.dto.ViajeResponseDTO;
import com.uade.be_tourapp.entity.Guia;
import com.uade.be_tourapp.entity.Servicio;
import com.uade.be_tourapp.entity.Usuario;
import com.uade.be_tourapp.entity.Viaje;
import com.uade.be_tourapp.enums.EstadosViajeEnum;
import com.uade.be_tourapp.exception.BadRequestException;
import com.uade.be_tourapp.repository.ServicioRepository;
import com.uade.be_tourapp.repository.ViajeRepository;
import com.uade.be_tourapp.state.EstadoViaje.impl.EstadoReservado;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ViajeService {

    private final ViajeRepository viajeRepository;
    private final UsuarioService usuarioService;
    private final ServicioRepository servicioRepository;

    public ViajeService(ViajeRepository viajeRepository, UsuarioService usuarioService, ServicioRepository servicioRepository) {
        this.viajeRepository = viajeRepository;
        this.usuarioService = usuarioService;
        this.servicioRepository = servicioRepository;
    }

    public ViajeResponseDTO generarResponse(Viaje viaje) {
        return ViajeResponseDTO.builder()
                .id(viaje.getId())
                .turistaId(viaje.getTurista().getId())
                .guiaId(viaje.getGuia().getId())
                .servicioId(viaje.getServicio().getId())
                .fechaInicio(viaje.getFechaInicio())
                .fechaFin(viaje.getFechaFin())
                .pais(viaje.getPais())
                .ciudad(viaje.getCiudad())
                .precio(viaje.getPrecio())
                .estado(viaje.getEstado().getNombre())
                .build();
    }

    public ViajeResponseDTO registrarViaje(ViajeRequestDTO viajeRequestDTO) {
        Usuario turista = usuarioService.obtenerAutenticado();
        Guia guia = usuarioService.getGuiaById(viajeRequestDTO.getGuiaId());
        Servicio servicio = servicioRepository.findById(viajeRequestDTO.getServicioId()).orElseThrow(() -> new BadRequestException("El servicio no existe."));

        Viaje viaje = Viaje.builder()
                .turista(turista)
                .guia(guia)
                .servicio(servicio)
                .fechaInicio(viajeRequestDTO.getFechaInicio())
                .fechaFin(viajeRequestDTO.getFechaFin())
                .pais(viajeRequestDTO.getPais())
                .ciudad(viajeRequestDTO.getCiudad())
                .precio(viajeRequestDTO.getPrecio())
                .build();

        viaje.cambiarEstado(new EstadoReservado());
        Viaje savedViaje = viajeRepository.save(viaje);

        return generarResponse(savedViaje);
    }

    public ViajeResponseDTO confirmarViaje(Integer viajeId) {
        Viaje viaje = viajeRepository
                .findById(viajeId)
                .orElseThrow(() -> new BadRequestException("El viaje no existe"));

        Guia guia;
        try {
            guia = (Guia) usuarioService.obtenerAutenticado();
        } catch (Exception e) {
            throw new BadRequestException("No sos un guía.");
        }

        if (!Objects.equals(viaje.getGuia().getId(), guia.getId())) {
            throw new BadRequestException("No estás autorizado."); // DT: se debería crear una excepción ForbiddenException.
        }

        viaje.confirmar();
        Viaje savedViaje = viajeRepository.save(viaje);

        return generarResponse(savedViaje);
    }

    public ViajeResponseDTO cancelarViaje(Integer viajeId) {
        Viaje viaje = viajeRepository
                .findById(viajeId)
                .orElseThrow(() -> new BadRequestException("El viaje no existe"));

        Usuario usuario = usuarioService.obtenerAutenticado();

        if (usuario instanceof Guia) {
            if (!Objects.equals(viaje.getGuia().getId(), usuario.getId())) {
                throw new BadRequestException("No estás autorizado."); // DT: se debería crear una excepción ForbiddenException.
            }
        } else {
            if (!Objects.equals(viaje.getTurista().getId(), usuario.getId())) {
                throw new BadRequestException("No estás autorizado."); // DT: se debería crear una excepción ForbiddenException.
            }
        }

        viaje.cancelar();
        Viaje savedViaje = viajeRepository.save(viaje);

        return generarResponse(savedViaje);
    }
}
