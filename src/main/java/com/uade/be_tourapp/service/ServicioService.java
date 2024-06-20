package com.uade.be_tourapp.service;

import com.uade.be_tourapp.dto.GenericResponseDTO;
import com.uade.be_tourapp.dto.servicio.ServicioRequestDTO;
import com.uade.be_tourapp.dto.servicio.ServicioResponseDTO;
import com.uade.be_tourapp.entity.Guia;
import com.uade.be_tourapp.entity.Servicio;
import com.uade.be_tourapp.exception.BadRequestException;
import com.uade.be_tourapp.repository.GuiaRepository;
import com.uade.be_tourapp.repository.ServicioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioService {
    private final ServicioRepository servicioRepository;
    private final UsuarioService usuarioService;
    private final GuiaRepository guiaRepository;

    public ServicioService(ServicioRepository servicioRepository, UsuarioService usuarioService, GuiaRepository guiaRepository) {
        this.servicioRepository = servicioRepository;
        this.usuarioService = usuarioService;
        this.guiaRepository = guiaRepository;
    }

    public Guia obtenerGuia() {
        Guia guia;
        try {
            guia = (Guia) usuarioService.obtenerAutenticado();
        } catch (Exception e) {
            throw new BadRequestException("Debes ser guia");
        }
        return guia;
    }

    public Servicio checkOwnerDelServicio(Integer id) {
        Guia guia = obtenerGuia();
        Servicio servicio = servicioRepository.findById(id).orElseThrow(() -> new BadRequestException("El servicio no existe"));
        if (!servicio.getGuia().getId().equals(guia.getId())) throw new BadRequestException("Bad request");
        return servicio;
    }

    public List<ServicioResponseDTO> obtenerServicios(Integer guiaId) {
        Guia guia = guiaRepository.findById(guiaId).orElseThrow(() -> new BadRequestException("El guia no existe."));
        return guia.getServicios().stream()
                .map(Servicio::toDto)
                .toList();
    }

    public ServicioResponseDTO crearServicio(ServicioRequestDTO servicioRequestDTO) {
        Guia guia = obtenerGuia();

        Servicio servicio = Servicio.builder()
                .tipo(servicioRequestDTO.getTipo())
                .precio(servicioRequestDTO.getPrecio())
                .pais(servicioRequestDTO.getPais())
                .ciudad(servicioRequestDTO.getCiudad())
                .guia(guia)
                .build();

        Servicio servicioCreado = servicioRepository.save(servicio);

        return ServicioResponseDTO.builder()
                .id(servicioCreado.getId())
                .tipo(servicioCreado.getTipo())
                .precio(servicioCreado.getPrecio())
                .ciudad(servicioCreado.getCiudad())
                .pais(servicioCreado.getPais())
                .build();
    }

    public GenericResponseDTO eliminarServicio(Integer id) {
        checkOwnerDelServicio(id);
        servicioRepository.deleteById(id);

        return GenericResponseDTO.builder()
                .message("Servicio eliminado.")
                .build();
    }

    public GenericResponseDTO cambiarPrecioServicio(Integer id, ServicioRequestDTO servicioRequestDTO) {
        Servicio servicio = checkOwnerDelServicio(id);
        servicio.setPrecio(servicioRequestDTO.getPrecio());
        servicioRepository.save(servicio);

        return GenericResponseDTO.builder()
                .message("Servicio modificado.")
                .build();
    }
}
