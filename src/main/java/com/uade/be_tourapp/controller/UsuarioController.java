package com.uade.be_tourapp.controller;

import com.uade.be_tourapp.dto.account.AccountInfoDTO;
import com.uade.be_tourapp.dto.auth.LoginRequestDTO;
import com.uade.be_tourapp.dto.auth.LoginResponseDTO;
import com.uade.be_tourapp.dto.auth.RegistroRequestDTO;
import com.uade.be_tourapp.dto.auth.RegistroResponseDTO;
import com.uade.be_tourapp.dto.kyc.KycGuiaRequestDTO;
import com.uade.be_tourapp.dto.kyc.KycRequestDTO;
import com.uade.be_tourapp.dto.kyc.KycResponseDTO;
import com.uade.be_tourapp.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<LoginResponseDTO> loginUsuario(@RequestBody @Validated LoginRequestDTO loginRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioService.loguear(loginRequestDTO));
    }

    @PutMapping("/kyc")
    public ResponseEntity<KycResponseDTO> generalKyc(@RequestBody @Validated KycRequestDTO kycRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioService.generalKyc(kycRequestDTO));
    }

    @PutMapping("/kyc/guia")
    public ResponseEntity<KycResponseDTO> guiaKyc(@RequestBody @Validated KycGuiaRequestDTO kycGuiaRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioService.guiaKyc(kycGuiaRequestDTO));
    }

    @GetMapping("")
    public ResponseEntity<AccountInfoDTO> getAccountInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.getAccountInfo());
    }
}
