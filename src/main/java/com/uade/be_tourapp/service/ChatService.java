package com.uade.be_tourapp.service;

import com.uade.be_tourapp.dto.chat.ChatDTO;
import com.uade.be_tourapp.dto.chat.MensajeDTO;
import com.uade.be_tourapp.entity.Chat;
import com.uade.be_tourapp.entity.Guia;
import com.uade.be_tourapp.entity.Mensaje;
import com.uade.be_tourapp.entity.Usuario;
import com.uade.be_tourapp.exception.BadRequestException;
import com.uade.be_tourapp.repository.ChatRepository;
import com.uade.be_tourapp.repository.MensajeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final MensajeRepository mensajeRepository;
    private final UsuarioService usuarioService;

    public ChatService(ChatRepository chatRepository, MensajeRepository mensajeRepository, UsuarioService usuarioService) {
        this.chatRepository = chatRepository;
        this.mensajeRepository = mensajeRepository;
        this.usuarioService = usuarioService;
    }

    public List<ChatDTO> obtenerChats() {
        Usuario usuario = usuarioService.obtenerAutenticado();
        List<Chat> chats = chatRepository.findAllByTuristaIdOrGuiaId(usuario.getId(), usuario.getId());
        return chats.stream()
                .map(Chat::toDto)
                .toList();
    }

    public void crearChat(Usuario turista, Guia guia) {
        if (chatRepository.existsByTuristaIdAndGuiaId(turista.getId(), guia.getId())) return;

        Chat chat = Chat.builder()
                .turista(turista)
                .guia(guia)
                .build();

        chatRepository.save(chat);
    }

    public void enviarMensaje(MensajeDTO mensajeDTO) {
        Usuario usuario = usuarioService.obtenerAutenticado();

        Chat chat = chatRepository.findById(mensajeDTO.getChatId()).orElseThrow(() -> new BadRequestException("El chat no existe."));
        if (!chat.getGuia().getId().equals(usuario.getId()) && !chat.getTurista().getId().equals(usuario.getId())) {
            throw new BadRequestException("Acceso denegado.");
        }

        Mensaje mensaje = Mensaje.builder()
                .fecha(LocalDateTime.now())
                .contenido(mensajeDTO.getContenido())
                .emisor(usuario)
                .chat(chat)
                .build();

        mensajeRepository.save(mensaje);
    }
}
