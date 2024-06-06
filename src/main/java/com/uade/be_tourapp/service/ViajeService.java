package com.uade.be_tourapp.service;

import com.uade.be_tourapp.dto.ViajeRequestDTO;
import com.uade.be_tourapp.dto.ViajeResponseDTO;
import com.uade.be_tourapp.entity.Guia;
import com.uade.be_tourapp.entity.Usuario;
import com.uade.be_tourapp.entity.Viaje;
import com.uade.be_tourapp.enums.EstadosViajeEnum;
import com.uade.be_tourapp.exception.BadRequestException;
import com.uade.be_tourapp.repository.ViajeRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ViajeService {

    private final ViajeRepository viajeRepository;
    private final UsuarioService usuarioService;

    public ViajeService(ViajeRepository viajeRepository, UsuarioService usuarioService) {
        this.viajeRepository = viajeRepository;
        this.usuarioService = usuarioService;
    }

    public ViajeResponseDTO generarResponse(Viaje viaje) {
        return ViajeResponseDTO.builder()
                .id(viaje.getId())
                .turistaId(viaje.getTurista().getId())
                .guiaId(viaje.getGuia().getId())
                .fechaInicio(viaje.getFechaInicio())
                .fechaFin(viaje.getFechaFin())
                .pais(viaje.getPais())
                .ciudad(viaje.getCiudad())
                .precio(viaje.getPrecio())
                .estado(viaje.getEstado())
                .build();
    }

    public ViajeResponseDTO registrarViaje(ViajeRequestDTO viajeRequestDTO) {
        Usuario turista = usuarioService.obtenerAutenticado();
        Guia guia = usuarioService.getGuiaById(viajeRequestDTO.getGuiaId());

        Viaje viaje = Viaje.builder()
                .turista(turista)
                .guia(guia)
                .fechaInicio(viajeRequestDTO.getFechaInicio())
                .fechaFin(viajeRequestDTO.getFechaFin())
                .pais(viajeRequestDTO.getPais())
                .ciudad(viajeRequestDTO.getCiudad())
                .precio(viajeRequestDTO.getPrecio())
                .estado(EstadosViajeEnum.RESERVADO)
                .build();

        Viaje savedViaje = viajeRepository.save(viaje);

        return generarResponse(savedViaje);
    }

    public ViajeResponseDTO confirmarViaje(Integer viajeId) {
        Viaje viaje = viajeRepository
                .findById(viajeId)
                .orElseThrow(() -> new BadRequestException("El viaje no existe"));

        Usuario turista = usuarioService.obtenerAutenticado();
        if (!Objects.equals(viaje.getTurista().getId(), turista.getId())) {
            throw new BadRequestException("No estás autorizado."); // DT: se debería crear una excepción ForbiddenException.
        }

        viaje.getEstadoViaje().confirmar(viaje);
        Viaje savedViaje = viajeRepository.save(viaje);

        return generarResponse(savedViaje);
    }

    public ViajeResponseDTO cancelarViaje(Integer viajeId) {
        Viaje viaje = viajeRepository
                .findById(viajeId)
                .orElseThrow(() -> new BadRequestException("El viaje no existe"));

        Usuario turista = usuarioService.obtenerAutenticado();
        if (!Objects.equals(viaje.getTurista().getId(), turista.getId())) {
            throw new BadRequestException("No estás autorizado."); // DT: se debería crear una excepción ForbiddenException.
        }

        viaje.getEstadoViaje().cancelar(viaje);
        Viaje savedViaje = viajeRepository.save(viaje);

        return generarResponse(savedViaje);
    }
}
