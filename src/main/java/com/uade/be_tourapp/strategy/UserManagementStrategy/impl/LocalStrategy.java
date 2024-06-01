package com.uade.be_tourapp.strategy.UserManagementStrategy.impl;

import com.uade.be_tourapp.dto.LoginRequestDTO;
import com.uade.be_tourapp.dto.RegistroRequestDTO;
import com.uade.be_tourapp.entity.Usuario;
import com.uade.be_tourapp.enums.AuthStrategiesEnum;
import com.uade.be_tourapp.exception.BadRequestException;
import com.uade.be_tourapp.repository.UsuarioRepository;
import com.uade.be_tourapp.strategy.UserManagementStrategy.AuthStrategy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class LocalStrategy implements AuthStrategy {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public LocalStrategy(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthStrategiesEnum getIdentification() {
        return AuthStrategiesEnum.LOCAL;
    }

    @Override
    public Usuario loguear(LoginRequestDTO loginRequestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getEmail(),
                        loginRequestDTO.getPassword()
                )
        );

        return usuarioRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow();
    }

    @Override
    public Usuario registrar(RegistroRequestDTO registroRequestDTO) {
        if (registroRequestDTO.getPassword() == null) throw new BadRequestException("Password is required.");
        return Usuario.builder()
                .email(registroRequestDTO.getEmail())
                .password(passwordEncoder.encode(registroRequestDTO.getPassword()))
                .proveedor(AuthStrategiesEnum.LOCAL)
                .build();
    }
}
