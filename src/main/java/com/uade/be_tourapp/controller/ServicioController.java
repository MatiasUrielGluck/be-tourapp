package com.uade.be_tourapp.controller;

import com.uade.be_tourapp.dto.GenericResponseDTO;
import com.uade.be_tourapp.dto.ServicioRequestDTO;
import com.uade.be_tourapp.dto.ServicioResponseDTO;
import com.uade.be_tourapp.service.ServicioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/servicio")
@RestController
public class ServicioController {
    private final ServicioService servicioService;

    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @PostMapping
    public ResponseEntity<ServicioResponseDTO> crearServicio(@RequestBody @Validated ServicioRequestDTO servicioRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(servicioService.crearServicio(servicioRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponseDTO> eliminarServicio(@PathVariable Integer id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(servicioService.eliminarServicio(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponseDTO> cambiarPrecioServicio(@PathVariable Integer id, @RequestBody ServicioRequestDTO servicioRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(servicioService.cambiarPrecioServicio(id, servicioRequestDTO));
    }
}
