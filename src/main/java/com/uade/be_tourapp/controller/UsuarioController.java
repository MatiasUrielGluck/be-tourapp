package com.uade.be_tourapp.controller;

import com.uade.be_tourapp.dto.usuario.AccountInfoDTO;
import com.uade.be_tourapp.dto.auth.LoginRequestDTO;
import com.uade.be_tourapp.dto.auth.LoginResponseDTO;
import com.uade.be_tourapp.dto.auth.RegistroRequestDTO;
import com.uade.be_tourapp.dto.auth.RegistroResponseDTO;
import com.uade.be_tourapp.dto.kyc.KycGuiaRequestDTO;
import com.uade.be_tourapp.dto.kyc.KycRequestDTO;
import com.uade.be_tourapp.dto.kyc.KycResponseDTO;
import com.uade.be_tourapp.dto.usuario.AccountUpdateRequestDTO;
import com.uade.be_tourapp.dto.usuario.FiltroDTO;
import com.uade.be_tourapp.dto.usuario.GuiaResponseDTO;
import com.uade.be_tourapp.enums.TipoServicioEnum;
import com.uade.be_tourapp.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    @PutMapping("/cuenta")
    public ResponseEntity<AccountInfoDTO> modificarCuenta(@RequestBody AccountUpdateRequestDTO accountUpdateRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioService.updateAccountInfo(accountUpdateRequestDTO));
    }

    @GetMapping("/guia/{id}")
    public ResponseEntity<GuiaResponseDTO> obtenerGuia(@PathVariable Integer id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioService.buscarGuia(id));
    }

    @GetMapping("/guia/disponibilidad/{id}")
    public ResponseEntity<Boolean> chequearDisponbilidad(
            @PathVariable Integer id,
            @RequestParam LocalDate fechaInicio,
            @RequestParam LocalDate fechaFin
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.isGuiaDisponible(id, fechaInicio, fechaFin));
    }

    @GetMapping("/guia/buscar")
    public ResponseEntity<List<GuiaResponseDTO>> buscarGuiasDisponibles(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String apellido,
            @RequestParam(required = false) String pais,
            @RequestParam(required = false) String ciudad,
            @RequestParam(required = false) TipoServicioEnum tipoServicio,
            @RequestParam(required = false) List<String> idiomas,
            @RequestParam LocalDate fechaInicio,
            @RequestParam LocalDate fechaFin
            ) {

        FiltroDTO filtros = FiltroDTO.builder()
                .nombre(nombre)
                .apellido(apellido)
                .pais(pais)
                .ciudad(ciudad)
                .tipoServicio(tipoServicio)
                .fechaInicio(fechaInicio)
                .fechaFin(fechaFin)
                .idiomas(idiomas)
                .build();

        return ResponseEntity.ok(usuarioService.buscarGuiasConFiltro(filtros));
    }
}
