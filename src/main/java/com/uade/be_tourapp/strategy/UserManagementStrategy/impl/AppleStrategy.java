package com.uade.be_tourapp.strategy.UserManagementStrategy.impl;

import com.uade.be_tourapp.dto.auth.LoginRequestDTO;
import com.uade.be_tourapp.dto.auth.RegistroRequestDTO;
import com.uade.be_tourapp.entity.Usuario;
import com.uade.be_tourapp.enums.AuthStrategiesEnum;
import com.uade.be_tourapp.repository.UsuarioRepository;
import com.uade.be_tourapp.strategy.UserManagementStrategy.AuthStrategy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AppleStrategy implements AuthStrategy {
    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final String token = "appleMockToken";

    public AppleStrategy(AuthenticationManager authenticationManager, UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthStrategiesEnum getIdentification() {
        return AuthStrategiesEnum.APPLE;
    }

    @Override
    public Usuario loguear(LoginRequestDTO loginRequestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getEmail(),
                        token
                )
        );

        return usuarioRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow();
    }

    @Override
    public Usuario registrar(RegistroRequestDTO registroRequestDTO) {
        return Usuario.builder()
                .email(registroRequestDTO.getEmail())
                .password(passwordEncoder.encode(token))
                .proveedor(AuthStrategiesEnum.APPLE)
                .build();
    }
}
