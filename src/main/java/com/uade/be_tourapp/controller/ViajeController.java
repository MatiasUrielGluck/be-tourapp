package com.uade.be_tourapp.controller;

import com.uade.be_tourapp.dto.ViajeRequestDTO;
import com.uade.be_tourapp.dto.ViajeResponseDTO;
import com.uade.be_tourapp.service.ViajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
