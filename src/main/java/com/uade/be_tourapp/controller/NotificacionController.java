package com.uade.be_tourapp.controller;

import com.uade.be_tourapp.dto.GenericResponseDTO;
import com.uade.be_tourapp.dto.notificacion.NotificacionDTO;
import com.uade.be_tourapp.service.NotificacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/notificacion")
@RestController
public class NotificacionController {
    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @GetMapping("")
    public ResponseEntity<List<NotificacionDTO>> obtenerNotificaciones() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(notificacionService.obtenerNotificaciones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponseDTO> leerNotificacion(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(notificacionService.leerNotificacion(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponseDTO> eliminarNotificacion(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(notificacionService.eliminarNotificacion(id));
    }
}
