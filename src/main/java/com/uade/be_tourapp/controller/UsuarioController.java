package com.uade.be_tourapp.controller;

import com.uade.be_tourapp.dto.LoginRequestDTO;
import com.uade.be_tourapp.dto.LoginResponseDTO;
import com.uade.be_tourapp.dto.RegistroRequestDTO;
import com.uade.be_tourapp.dto.RegistroResponseDTO;
import com.uade.be_tourapp.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/usuario")
@RestController
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/signup")
    public ResponseEntity<RegistroResponseDTO> registrarUsuario(@RequestBody @Validated RegistroRequestDTO registroRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioService.registrar(registroRequestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUsuario(@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioService.loguear(loginRequestDTO));
    }
}
