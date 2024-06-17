package com.uade.be_tourapp.service;

import com.uade.be_tourapp.dto.servicio.ServicioResponseDTO;
import com.uade.be_tourapp.dto.usuario.AccountInfoDTO;
import com.uade.be_tourapp.dto.auth.LoginRequestDTO;
import com.uade.be_tourapp.dto.auth.LoginResponseDTO;
import com.uade.be_tourapp.dto.auth.RegistroRequestDTO;
import com.uade.be_tourapp.dto.auth.RegistroResponseDTO;
import com.uade.be_tourapp.dto.kyc.KycGuiaRequestDTO;
import com.uade.be_tourapp.dto.kyc.KycRequestDTO;
import com.uade.be_tourapp.dto.kyc.KycResponseDTO;
import com.uade.be_tourapp.dto.usuario.FiltroDTO;
import com.uade.be_tourapp.dto.usuario.GuiaResponseDTO;
import com.uade.be_tourapp.entity.Credencial;
import com.uade.be_tourapp.entity.Guia;
import com.uade.be_tourapp.entity.Usuario;
import com.uade.be_tourapp.entity.Viaje;
import com.uade.be_tourapp.enums.AuthStrategiesEnum;
import com.uade.be_tourapp.enums.RolUsuarioEnum;
import com.uade.be_tourapp.exception.BadRequestException;
import com.uade.be_tourapp.exception.UserAlreadyExistsException;
import com.uade.be_tourapp.repository.GuiaRepository;
import com.uade.be_tourapp.repository.UsuarioRepository;
import com.uade.be_tourapp.repository.ViajeRepository;
import com.uade.be_tourapp.strategy.UserManagementStrategy.AuthStrategy;
import com.uade.be_tourapp.utils.Base64Utils;
import com.uade.be_tourapp.utils.ViajeSpecification;
import io.micrometer.common.util.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.uade.be_tourapp.utils.GuiaSpecification.*;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final List<AuthStrategy> authStrategies;
    private final CredencialService credencialService;
    private final GuiaRepository guiaRepository;
    private final ViajeRepository viajeRepository;
    private AuthStrategy authStrategy;

    public UsuarioService(List<AuthStrategy> authStrategies, JwtService jwtService, UsuarioRepository usuarioRepository, CredencialService credencialService, GuiaRepository guiaRepository, ViajeRepository viajeRepository) {
        this.authStrategies = authStrategies;
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
        this.credencialService = credencialService;
        this.guiaRepository = guiaRepository;
        this.viajeRepository = viajeRepository;
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

        Optional<Usuario> dniExistente = usuarioRepository.findByDni(input.getDni());
        if (dniExistente.isPresent() && !Objects.equals(dniExistente.get().getId(), usuario.getId())) {
            throw new BadRequestException("Ya existe un usuario con ese dni");
        }

        usuario.setNombre(input.getNombre());
        usuario.setApellido(input.getApellido());
        usuario.setGenero(input.getGenero());
        usuario.setDni(input.getDni());
        usuario.setNumTelefono(input.getNumTelefono());
        usuario.setFoto(Base64Utils.base64ToBytes(input.getFoto()));
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
                .foto(Base64Utils.base64ToBytes(input.getFoto()))
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
                .foto(usuario.getFoto() != null ? Base64Utils.bytesToBase64(usuario.getFoto()) : "")
                .kycCompleted(usuario.getKycCompleted())
                .build();
    }

    public Guia getGuiaById(Integer id) {
        return (Guia) usuarioRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Guia no encontrado"));
    }

    public Boolean isGuiaDisponible(Integer id, LocalDate fechaInicio, LocalDate fechaFin) {
        Guia guia = getGuiaById(id);
        if (!guia.getKycCompleted()) return false;

        Specification<Viaje> spec = ViajeSpecification.guiaDisponible(id, fechaInicio, fechaFin);
        List<Viaje> viajes = viajeRepository.findAll(spec);
        return viajes.isEmpty();
    }

    public GuiaResponseDTO generarGuiaResponse(Guia guia) {
        List<ServicioResponseDTO> servicios = guia
                .getServicios()
                .stream()
                .map(servicio -> ServicioResponseDTO.builder()
                        .id(servicio.getId())
                        .tipo(servicio.getTipo())
                        .precio(servicio.getPrecio())
                        .ciudad(servicio.getCiudad())
                        .pais(servicio.getPais())
                        .build())
                .toList();

        return GuiaResponseDTO.builder()
                .id(guia.getId())
                .nombre(guia.getNombre())
                .apellido(guia.getApellido())
                .email(guia.getEmail())
                .genero(guia.getGenero())
                .dni(guia.getDni())
                .foto(guia.getFoto() != null ? Base64Utils.bytesToBase64(guia.getFoto()) : "")
                .credencial(guia.getCredencial())
                .servicios(servicios)
                .build();
    }

    public GuiaResponseDTO buscarGuia(Integer id) {
        Guia guia = getGuiaById(id);
        return generarGuiaResponse(guia);
    }

    public List<GuiaResponseDTO> buscarGuiasConFiltro(FiltroDTO filtroDTO) {
        Specification<Guia> filtros = Specification
                .where(StringUtils.isBlank(filtroDTO.getNombre()) ? null : nombreLike(filtroDTO.getNombre()))
                .and(StringUtils.isBlank(filtroDTO.getApellido()) ? null : apellidoLike(filtroDTO.getApellido()))
                .and(StringUtils.isBlank(filtroDTO.getPais()) ? null : paisLikeInServicio(filtroDTO.getPais()))
                .and(StringUtils.isBlank(filtroDTO.getCiudad()) ? null : ciudadLikeInServicio(filtroDTO.getCiudad()));

        return guiaRepository.findAll(filtros)
                .stream()
                .filter(guia -> isGuiaDisponible(guia.getId(), filtroDTO.getFechaInicio(), filtroDTO.getFechaFin()))
                .map(this::generarGuiaResponse)
                .toList();
    }
}
