package com.uade.be_tourapp.service;

import com.uade.be_tourapp.dto.ViajeRequestDTO;
import com.uade.be_tourapp.dto.ViajeResponseDTO;
import com.uade.be_tourapp.entity.Guia;
import com.uade.be_tourapp.entity.Turista;
import com.uade.be_tourapp.entity.Usuario;
import com.uade.be_tourapp.entity.Viaje;
import com.uade.be_tourapp.repository.UsuarioRepository;
import com.uade.be_tourapp.repository.ViajeRepository;
import org.springframework.stereotype.Service;

@Service
public class ViajeService {

    private final ViajeRepository viajeRepository;
    private final UsuarioRepository usuarioRepository;

    public ViajeService(ViajeRepository viajeRepository, UsuarioRepository usuarioRepository) {
        this.viajeRepository = viajeRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public ViajeResponseDTO registrarViaje(ViajeRequestDTO viajeRequestDTO) {
        // Buscar el guía en la base de datos
        Guia guia = (Guia)usuarioRepository.findById(viajeRequestDTO.getGuiaId())
                .orElseThrow(() -> new RuntimeException("Guía no encontrado"));

        // Buscar el turista en la base de datos utilizando el ID proporcionado
        Usuario turista = usuarioRepository.findById(viajeRequestDTO.getTuristaId())
                .orElseThrow(() -> new RuntimeException("Turista no encontrado"));

        // Crear un nuevo viaje
        Viaje viaje = new Viaje();
        viaje.setTurista(turista);
        viaje.setGuia(guia);
        viaje.setFechaInicio(viajeRequestDTO.getFecha());
        viaje.setFechaFin(viajeRequestDTO.getFecha());
        viaje.setPrecio(viajeRequestDTO.getPrecio());

        // Guardar el viaje en la base de datos
        Viaje savedViaje = viajeRepository.save(viaje);

        // Crear el DTO de respuesta
        return ViajeResponseDTO.builder()
                .id(savedViaje.getId())
                .turistaId(savedViaje.getId())
                .guiaId(savedViaje.getGuia().getId())
                .fechaInicio(savedViaje.getFechaInicio())
                .fechaFin(savedViaje.getFechaFin())
                .precio(savedViaje.getPrecio())
                .build();
    }
}
