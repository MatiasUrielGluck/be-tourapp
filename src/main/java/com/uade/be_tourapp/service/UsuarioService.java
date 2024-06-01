package com.uade.be_tourapp.service;

import com.uade.be_tourapp.dto.LoginRequestDTO;
import com.uade.be_tourapp.dto.LoginResponseDTO;
import com.uade.be_tourapp.dto.RegistroRequestDTO;
import com.uade.be_tourapp.dto.RegistroResponseDTO;
import com.uade.be_tourapp.entity.Usuario;
import com.uade.be_tourapp.exception.UserAlreadyExistsException;
import com.uade.be_tourapp.repository.UsuarioRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public RegistroResponseDTO registrar(RegistroRequestDTO input) {
        if (usuarioRepository.existsByEmail(input.getEmail())) throw new UserAlreadyExistsException(input.getEmail());

        Usuario user = Usuario.builder()
                .email(input.getEmail())
                .password(passwordEncoder.encode(input.getPassword()))
                .nombre(input.getNombre())
                .apellido(input.getApellido())
                .sexo(input.getSexo())
                .dni(input.getDni())
                .numTelefono(input.getNumTelefono())
                .foto(input.getFoto())
                .build();

        usuarioRepository.save(user);
        return RegistroResponseDTO.builder()
                .mensaje("Usuario registrado exitosamente")
                .build();
    }

    public LoginResponseDTO loguear(LoginRequestDTO input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        Usuario usuario = usuarioRepository.findByEmail(input.getEmail())
                .orElseThrow();

        String jwtToken = jwtService.generateToken(usuario);
        return LoginResponseDTO.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();
    }
}
