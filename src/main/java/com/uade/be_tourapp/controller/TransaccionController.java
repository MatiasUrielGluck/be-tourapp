package com.uade.be_tourapp.controller;

import com.uade.be_tourapp.dto.FacturaDTO;
import com.uade.be_tourapp.dto.GenericResponseDTO;
import com.uade.be_tourapp.service.TransaccionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/transaccion")
@RestController
public class TransaccionController {
    private final TransaccionService transaccionService;

    public TransaccionController(TransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponseDTO> pagarFactura(@PathVariable Integer id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(transaccionService.pagarFactura(id));
    }

    @GetMapping("/viaje/{viajeId}")
    public ResponseEntity<List<FacturaDTO>> obtenerDocumentosDeViaje(@PathVariable Integer viajeId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(transaccionService.obtenerFacturasDeViaje(viajeId));
    }
}
