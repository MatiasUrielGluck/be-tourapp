package com.uade.be_tourapp.controller;

import com.uade.be_tourapp.dto.GenericResponseDTO;
import com.uade.be_tourapp.dto.chat.ChatDTO;
import com.uade.be_tourapp.dto.chat.MensajeDTO;
import com.uade.be_tourapp.service.ChatService;
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

    @PostMapping("/msg")
    public ResponseEntity<GenericResponseDTO> enviarMensaje(@RequestBody MensajeDTO mensajeDTO) {
        chatService.enviarMensaje(mensajeDTO);
        return ResponseEntity.ok(GenericResponseDTO.builder().message("Enviado.").build());
    }
}
