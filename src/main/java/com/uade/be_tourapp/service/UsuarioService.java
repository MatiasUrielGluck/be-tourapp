package com.uade.be_tourapp.service;

import com.uade.be_tourapp.dto.LoginRequestDTO;
import com.uade.be_tourapp.dto.LoginResponseDTO;
import com.uade.be_tourapp.dto.RegistroRequestDTO;
import com.uade.be_tourapp.dto.RegistroResponseDTO;
import com.uade.be_tourapp.entity.Usuario;
import com.uade.be_tourapp.enums.AuthStrategiesEnum;
import com.uade.be_tourapp.exception.UserAlreadyExistsException;
import com.uade.be_tourapp.repository.UsuarioRepository;
import com.uade.be_tourapp.strategy.UserManagementStrategy.AuthStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final List<AuthStrategy> authStrategies;
    private AuthStrategy authStrategy;

    public UsuarioService(List<AuthStrategy> authStrategies, JwtService jwtService, UsuarioRepository usuarioRepository) {
        this.authStrategies = authStrategies;
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
    }

    public void cambiarAuthStrategy(AuthStrategiesEnum estrategia) {
        this.authStrategy = authStrategies.stream()
                .filter(strategy -> strategy.getIdentification() == estrategia)
                .findAny()
                .orElseThrow();
    }

    public RegistroResponseDTO registrar(RegistroRequestDTO input) {
        if (usuarioRepository.existsByEmail(input.getEmail())) throw new UserAlreadyExistsException(input.getEmail());

        cambiarAuthStrategy(input.getAuthStrategy());
        Usuario nuevoUsuario = authStrategy.registrar(input);

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
}
