package com.uade.be_tourapp.service;

import com.uade.be_tourapp.dto.*;
import com.uade.be_tourapp.entity.Credencial;
import com.uade.be_tourapp.entity.Guia;
import com.uade.be_tourapp.entity.Usuario;
import com.uade.be_tourapp.enums.AuthStrategiesEnum;
import com.uade.be_tourapp.enums.RolUsuarioEnum;
import com.uade.be_tourapp.exception.BadRequestException;
import com.uade.be_tourapp.exception.UserAlreadyExistsException;
import com.uade.be_tourapp.repository.UsuarioRepository;
import com.uade.be_tourapp.strategy.UserManagementStrategy.AuthStrategy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final List<AuthStrategy> authStrategies;
    private final CredencialService credencialService;
    private AuthStrategy authStrategy;

    public UsuarioService(List<AuthStrategy> authStrategies, JwtService jwtService, UsuarioRepository usuarioRepository, CredencialService credencialService) {
        this.authStrategies = authStrategies;
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
        this.credencialService = credencialService;
    }

    public void cambiarAuthStrategy(AuthStrategiesEnum estrategia) {
        this.authStrategy = authStrategies.stream()
                .filter(strategy -> strategy.getIdentification() == estrategia)
                .findAny()
                .orElseThrow();
    }

    public Usuario obtenerAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Usuario) authentication.getPrincipal();
    }

    public RegistroResponseDTO registrar(RegistroRequestDTO input) {
        if (usuarioRepository.existsByEmail(input.getEmail())) throw new UserAlreadyExistsException(input.getEmail());

        cambiarAuthStrategy(input.getAuthStrategy());
        Usuario nuevoUsuario = authStrategy.registrar(input);
        nuevoUsuario.setKycCompleted(false);

        usuarioRepository.save(nuevoUsuario);
        return RegistroResponseDTO.builder()
                .mensaje("Usuario registrado exitosamente")
                .build();
    }

    public LoginResponseDTO loguear(LoginRequestDTO input) {
        cambiarAuthStrategy(input.getAuthStrategy());
        Usuario usuario = authStrategy.loguear(input);
        String jwtToken = jwtService.generateToken(usuario);
        return LoginResponseDTO.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();
    }

    public KycResponseDTO generalKyc(KycRequestDTO input) {
        Usuario usuario = obtenerAutenticado();

        if (usuarioRepository.existsByDni(input.getDni())) {
            throw new BadRequestException("Ya existe un usuario con ese dni");
        }

        usuario.setNombre(input.getNombre());
        usuario.setApellido(input.getApellido());
        usuario.setGenero(input.getGenero());
        usuario.setDni(input.getDni());
        usuario.setNumTelefono(input.getNumTelefono());
        usuario.setFoto(input.getFoto());
        usuario.setKycCompleted(input.getRol() != RolUsuarioEnum.GUIA);

        usuarioRepository.save(usuario);

        return KycResponseDTO.builder()
                .kycCompleted(usuario.getKycCompleted())
                .build();
    }

    public KycResponseDTO guiaKyc(KycGuiaRequestDTO input) {
        Credencial credencial = Credencial.builder()
                .numero(input.getNumero())
                .vencimiento(input.getVencimiento())
                .foto(input.getFoto())
                .build();

        if (!credencialService.esCredencialValida(credencial)) throw new BadRequestException("La credencial proporcionada es inválida.");

        Usuario autenticado = obtenerAutenticado();
        usuarioRepository.updateUserSetRolForId("GUIA", autenticado.getId());

        Guia guia = (Guia) usuarioRepository.findByEmail(autenticado.getEmail())
                .orElseThrow();

        guia.setCredencial(credencial);
        guia.setKycCompleted(true);
        usuarioRepository.save(guia);

        return KycResponseDTO.builder()
                .kycCompleted(true)
                .build();
    }

    public AccountInfoDTO getAccountInfo() {
        Usuario usuario = obtenerAutenticado();
        return AccountInfoDTO.builder()
                .email(usuario.getEmail())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .genero(usuario.getGenero())
                .dni(usuario.getDni())
                .numTelefono(usuario.getNumTelefono())
                .foto(usuario.getFoto())
                .kycCompleted(usuario.getKycCompleted())
                .build();
    }

    public Guia getGuiaById(Integer id) {
        return (Guia) usuarioRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Guia no encontrado"));
    }
}
