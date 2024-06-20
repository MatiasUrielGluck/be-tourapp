package com.uade.be_tourapp.service;

import com.uade.be_tourapp.dto.viaje.ViajeRequestDTO;
import com.uade.be_tourapp.dto.viaje.ViajeResponseDTO;
import com.uade.be_tourapp.dto.viaje.ViajeReviewDTO;
import com.uade.be_tourapp.entity.*;
import com.uade.be_tourapp.enums.EstadosViajeEnum;
import com.uade.be_tourapp.exception.BadRequestException;
import com.uade.be_tourapp.repository.ReviewRepository;
import com.uade.be_tourapp.repository.ServicioRepository;
import com.uade.be_tourapp.repository.ViajeRepository;
import com.uade.be_tourapp.state.EstadoViaje.EstadoViaje;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ViajeService {

    private final ViajeRepository viajeRepository;
    private final UsuarioService usuarioService;
    private final ServicioRepository servicioRepository;
    private final List<EstadoViaje> estadosViaje;
    private final ReviewRepository reviewRepository;

    public ViajeService(ViajeRepository viajeRepository, UsuarioService usuarioService, ServicioRepository servicioRepository, List<EstadoViaje> estadosViaje, ReviewRepository reviewRepository) {
        this.viajeRepository = viajeRepository;
        this.usuarioService = usuarioService;
        this.servicioRepository = servicioRepository;
        this.estadosViaje = estadosViaje;
        this.reviewRepository = reviewRepository;
    }

    public Viaje obtenerViajeGuiaAutorizado(Integer viajeId) {
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

        return viaje;
    }

    public List<ViajeReviewDTO> obtenerViajes() {
        Usuario usuario = usuarioService.obtenerAutenticado();


        List<ViajeResponseDTO> viajes = viajeRepository
                .findAllByGuiaIdOrTuristaId(usuario.getId(), usuario.getId())
                .stream()
                .map(Viaje::toDto)
                .toList();

        List<ViajeReviewDTO> viajesReviewDTO = new ArrayList<>();

        for (ViajeResponseDTO viaje : viajes) {
            Review review = reviewRepository.findByViajeId(viaje.getId()).orElse(null);

            ViajeReviewDTO viajeDTO = ViajeReviewDTO.builder()
                    .viaje(viaje)
                    .review(review != null ? review.toDto() : null)
                    .build();
            viajesReviewDTO.add(viajeDTO);
        }

        return viajesReviewDTO;
    }

    public ViajeResponseDTO registrarViaje(ViajeRequestDTO viajeRequestDTO) {
        Usuario turista = usuarioService.obtenerAutenticado();
        Guia guia = usuarioService.getGuiaById(viajeRequestDTO.getGuiaId());
        Servicio servicio = servicioRepository.findById(viajeRequestDTO.getServicioId()).orElseThrow(() -> new BadRequestException("El servicio no existe."));

        if (!usuarioService.isGuiaDisponible(guia.getId(), viajeRequestDTO.getFechaInicio(), viajeRequestDTO.getFechaFin())) {
            throw new BadRequestException("El guía está ocupado.");
        }

        Viaje viaje = Viaje.builder()
                .turista(turista)
                .guia(guia)
                .servicio(servicio)
                .fechaInicio(viajeRequestDTO.getFechaInicio())
                .fechaFin(viajeRequestDTO.getFechaFin())
                .pais(viajeRequestDTO.getPais())
                .ciudad(viajeRequestDTO.getCiudad())
                .estadoEnum(EstadosViajeEnum.INICIALIZADO)
                .build();

        Viaje savedViaje = viajeRepository.save(viaje);
        savedViaje.inicializarEstado(estadosViaje);
        savedViaje.reservar();

        Viaje viajeReservado = viajeRepository.save(savedViaje);
        return viajeReservado.toDto();
    }

    public ViajeResponseDTO confirmarViaje(Integer viajeId) {
        Viaje viaje = obtenerViajeGuiaAutorizado(viajeId);
        viaje.inicializarEstado(estadosViaje);
        viaje.confirmar();
        Viaje savedViaje = viajeRepository.save(viaje);
        return savedViaje.toDto();
    }

    public ViajeResponseDTO cancelarViaje(Integer viajeId) {
        Viaje viaje = viajeRepository
                .findById(viajeId)
                .orElseThrow(() -> new BadRequestException("El viaje no existe"));
        viaje.inicializarEstado(estadosViaje);

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

        viaje.cancelar(usuario);
        Viaje savedViaje = viajeRepository.save(viaje);
        return savedViaje.toDto();
    }

    public ViajeResponseDTO concluirViaje(Integer viajeId) {
        Viaje viaje = obtenerViajeGuiaAutorizado(viajeId);
        viaje.inicializarEstado(estadosViaje);
        viaje.concluir();
        Viaje savedViaje = viajeRepository.save(viaje);
        return savedViaje.toDto();
    }
}
