package com.uade.be_tourapp.controller;

import com.uade.be_tourapp.dto.ViajeRequestDTO;
import com.uade.be_tourapp.dto.ViajeResponseDTO;
import com.uade.be_tourapp.service.ViajeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/viaje")
@RestController
public class ViajeController {

    private final ViajeService viajeService;

    public ViajeController(ViajeService viajeService){this.viajeService = viajeService; }

    @PostMapping ("/registrar")
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
