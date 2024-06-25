package com.uade.be_tourapp.controller;

import com.uade.be_tourapp.dto.chat.ChatDTO;
import com.uade.be_tourapp.dto.chat.MensajeDTO;
import com.uade.be_tourapp.dto.chat.MensajeResponseDTO;
import com.uade.be_tourapp.service.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/chat")
@RestController
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping
    public ResponseEntity<List<ChatDTO>> obtenerChats() {
        return ResponseEntity.ok(chatService.obtenerChats());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<MensajeResponseDTO>> obtenerMensajesPorChat(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(chatService.obtenerMensajes(id));
    }

    @PostMapping("/msg")
    public ResponseEntity<MensajeResponseDTO> enviarMensaje(@RequestBody MensajeDTO mensajeDTO) {
        return ResponseEntity.ok(chatService.enviarMensaje(mensajeDTO));
    }
}
