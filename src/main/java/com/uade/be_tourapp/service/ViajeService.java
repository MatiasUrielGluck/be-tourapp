package com.uade.be_tourapp.service;

import com.uade.be_tourapp.dto.ViajeRequestDTO;
import com.uade.be_tourapp.dto.ViajeResponseDTO;
import com.uade.be_tourapp.entity.Guia;
import com.uade.be_tourapp.entity.Usuario;
import com.uade.be_tourapp.entity.Viaje;
import com.uade.be_tourapp.repository.ViajeRepository;
import org.springframework.stereotype.Service;

@Service
public class ViajeService {

    private final ViajeRepository viajeRepository;
    private final UsuarioService usuarioService;

    public ViajeService(ViajeRepository viajeRepository, UsuarioService usuarioService) {
        this.viajeRepository = viajeRepository;
        this.usuarioService = usuarioService;
    }

    public ViajeResponseDTO registrarViaje(ViajeRequestDTO viajeRequestDTO) {
        Usuario turista = usuarioService.obtenerAutenticado();
        Guia guia = usuarioService.getGuiaById(viajeRequestDTO.getGuiaId());

        Viaje viaje = Viaje.builder()
                .turista(turista)
                .guia(guia)
                .fechaInicio(viajeRequestDTO.getFechaInicio())
                .fechaFin(viajeRequestDTO.getFechaFin())
                .precio(viajeRequestDTO.getPrecio())
                .build();

        Viaje savedViaje = viajeRepository.save(viaje);

        return ViajeResponseDTO.builder()
                .id(savedViaje.getId())
                .turistaId(savedViaje.getTurista().getId())
                .guiaId(savedViaje.getGuia().getId())
                .fechaInicio(savedViaje.getFechaInicio())
                .fechaFin(savedViaje.getFechaFin())
                .precio(savedViaje.getPrecio())
                .build();
    }
}
