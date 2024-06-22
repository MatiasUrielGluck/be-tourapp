package com.uade.be_tourapp.controller;

import com.uade.be_tourapp.dto.viaje.ViajeRequestDTO;
import com.uade.be_tourapp.dto.viaje.ViajeResponseDTO;
import com.uade.be_tourapp.dto.viaje.ViajeReviewDTO;
import com.uade.be_tourapp.service.ViajeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/viaje")
@RestController
public class ViajeController {

    private final ViajeService viajeService;

    public ViajeController(ViajeService viajeService){this.viajeService = viajeService; }

    @GetMapping
    public ResponseEntity<List<ViajeReviewDTO>> getViajes() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(viajeService.obtenerViajes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViajeReviewDTO> getViajeById(@PathVariable Integer id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(viajeService.obtenerViajeById(id));
    }

    @PostMapping("/registrar")
    public ResponseEntity<ViajeResponseDTO> registrarViaje(@RequestBody @Validated ViajeRequestDTO viajeRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(viajeService.registrarViaje(viajeRequestDTO));

    }

    @GetMapping("/confirmar/{id}")
    public ResponseEntity<ViajeResponseDTO> confirmarViaje(@PathVariable Integer id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(viajeService.confirmarViaje(id));
    }

    @GetMapping("/cancelar/{id}")
    public ResponseEntity<ViajeResponseDTO> cancelarViaje(@PathVariable Integer id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(viajeService.cancelarViaje(id));
    }

    @GetMapping("/concluir/{id}")
    public ResponseEntity<ViajeResponseDTO> concluirViaje(@PathVariable Integer id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(viajeService.concluirViaje(id));
    }
}
