package com.uade.be_tourapp.service;

import com.uade.be_tourapp.dto.usuario.*;
import com.uade.be_tourapp.dto.auth.LoginRequestDTO;
import com.uade.be_tourapp.dto.auth.LoginResponseDTO;
import com.uade.be_tourapp.dto.auth.RegistroRequestDTO;
import com.uade.be_tourapp.dto.auth.RegistroResponseDTO;
import com.uade.be_tourapp.dto.kyc.KycGuiaRequestDTO;
import com.uade.be_tourapp.dto.kyc.KycRequestDTO;
import com.uade.be_tourapp.dto.kyc.KycResponseDTO;
import com.uade.be_tourapp.entity.*;
import com.uade.be_tourapp.enums.AuthStrategiesEnum;
import com.uade.be_tourapp.enums.RolUsuarioEnum;
import com.uade.be_tourapp.enums.notificacion.MensajesEnum;
import com.uade.be_tourapp.enums.notificacion.NotificacionStrategyEnum;
import com.uade.be_tourapp.exception.BadRequestException;
import com.uade.be_tourapp.exception.UserAlreadyExistsException;
import com.uade.be_tourapp.repository.GuiaRepository;
import com.uade.be_tourapp.repository.IdiomaRepository;
import com.uade.be_tourapp.repository.UsuarioRepository;
import com.uade.be_tourapp.repository.ViajeRepository;
import com.uade.be_tourapp.strategy.UserManagementStrategy.AuthStrategy;
import com.uade.be_tourapp.utils.Base64Utils;
import com.uade.be_tourapp.utils.specification.ViajeSpecification;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static com.uade.be_tourapp.utils.specification.GuiaSpecification.*;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final IdiomaRepository idiomaRepository;
    private final JwtService jwtService;
    private final List<AuthStrategy> authStrategies;
    private final CredencialService credencialService;
    private final GuiaRepository guiaRepository;
    private final ViajeRepository viajeRepository;
    private AuthStrategy authStrategy;
    private ReviewService reviewService;
    private NotificacionService notificacionService;

    public UsuarioService(IdiomaRepository idiomaRepository, List<AuthStrategy> authStrategies, JwtService jwtService, UsuarioRepository usuarioRepository, CredencialService credencialService, GuiaRepository guiaRepository, ViajeRepository viajeRepository) {
        this.idiomaRepository = idiomaRepository;
        this.authStrategies = authStrategies;
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
        this.credencialService = credencialService;
        this.guiaRepository = guiaRepository;
        this.viajeRepository = viajeRepository;
    }

    @Autowired
    public void setReviewService(@Lazy ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Autowired
    public void setNotificacionService(@Lazy NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
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
        usuario.setFoto(!input.getFoto().isEmpty() ? Base64Utils.base64ToBytes(input.getFoto()) : null);
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
                .foto(!input.getFoto().isEmpty() ? Base64Utils.base64ToBytes(input.getFoto()) : null)
                .build();

        if (!credencialService.esCredencialValida(credencial)) throw new BadRequestException("La credencial proporcionada es inválida.");

        Usuario autenticado = obtenerAutenticado();
        usuarioRepository.updateUserSetRolForId("GUIA", autenticado.getId());

        Guia guia = (Guia) usuarioRepository.findByEmail(autenticado.getEmail())
                .orElseThrow();

        List<Idioma> idiomas = new ArrayList<>();
        for (String nombre : input.getIdiomas()) {
            Idioma idioma = idiomaRepository
                    .findByNombre(nombre)
                    .orElseThrow(() -> new BadRequestException("El idioma especificado no existe."));
            idiomas.add(idioma);
        }

        guia.setCredencial(credencial);
        guia.setIdiomas(idiomas);
        guia.setKycCompleted(true);
        usuarioRepository.save(guia);

        // Generar la push notification
        Notificacion notificacion = Notificacion.builder()
                .usuario(guia)
                .mensaje(MensajesEnum.GUIA_READY.getMensaje())
                .fecha(LocalDateTime.now())
                .visto(false)
                .build();
        notificacionService.cambiarEstrategia(NotificacionStrategyEnum.PUSH);
        notificacionService.notificar(notificacion);

        return KycResponseDTO.builder()
                .kycCompleted(true)
                .build();
    }

    public AccountInfoDTO updateAccountInfo(AccountUpdateRequestDTO input) {
        Usuario usuario = obtenerAutenticado();

        if (input.getNombre() != null && !input.getNombre().isEmpty()) usuario.setNombre(input.getNombre());
        if (input.getApellido() != null && !input.getApellido().isEmpty()) usuario.setApellido(input.getApellido());
        if (input.getFoto() != null) usuario.setFoto(Base64Utils.base64ToBytes(input.getFoto()));
        if (input.getNumTelefono() != null) usuario.setNumTelefono(input.getNumTelefono());
        if (input.getGenero() != null) usuario.setGenero(input.getGenero());

        usuarioRepository.save(usuario);
        return usuario.toDto();
    }

    public AccountInfoDTO getAccountInfo() {
        Usuario usuario = obtenerAutenticado();
        return usuario.toDto();
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

    public GuiaResponseDTO generarGuiaResponse(Guia guia, GuiaResponseOptions options) {
        List<Review> reviews = reviewService.obtenerReviewsGuia(guia.getId());
        Double puntuacion = reviewService.calcularPuntuacionGuia(reviews);

        return GuiaResponseDTO.builder()
                .id(guia.getId())
                .nombre(guia.getNombre())
                .apellido(guia.getApellido())
                .email(guia.getEmail())
                .genero(guia.getGenero())
                .dni(guia.getDni())
                .foto(guia.getFoto() != null ? Base64Utils.bytesToBase64(guia.getFoto()) : "")
                .credencial(options.getIncluirCredencial() ? guia.getCredencial() : null)
                .servicios(guia.getServicios().stream().map(Servicio::toDto).toList())
                .puntuacion(puntuacion)
                .reviewCount(reviews.size())
                .reviews(options.getIncluirReviews() ? reviews.stream().map(Review::toDto).toList() : null)
                .idiomas(guia.getIdiomas())
                .build();
    }

    public GuiaResponseDTO buscarGuia(Integer id) {
        Guia guia = getGuiaById(id);
        GuiaResponseOptions options = GuiaResponseOptions.builder()
                .incluirCredencial(true)
                .incluirReviews(true)
                .build();
        return generarGuiaResponse(guia, options);
    }

    public List<GuiaResponseDTO> buscarGuiasConFiltro(FiltroDTO filtroDTO) {
        Specification<Guia> filtros = Specification
                .where(StringUtils.isBlank(filtroDTO.getNombre()) ? null : nombreLike(filtroDTO.getNombre()))
                .and(StringUtils.isBlank(filtroDTO.getApellido()) ? null : apellidoLike(filtroDTO.getApellido()))
                .and(StringUtils.isBlank(filtroDTO.getPais()) ? null : paisLikeInServicio(filtroDTO.getPais()))
                .and(filtroDTO.getTipoServicio() == null ? null : tipoServicioInServicio(filtroDTO.getTipoServicio(), filtroDTO.getCiudad()))
                .and(StringUtils.isBlank(filtroDTO.getCiudad()) ? null : ciudadLikeInServicio(filtroDTO.getCiudad()));

        List<Guia> guias = guiaRepository.findAll(filtros);

        // Filtro por idiomas
        if (filtroDTO.getIdiomas() != null && !filtroDTO.getIdiomas().isEmpty()) {
            guias = guias.stream()
                    .filter(guia -> {
                        List<String> idiomas = guia.getIdiomas().stream().map(Idioma::getNombre).toList();
                        return new HashSet<>(idiomas).containsAll(filtroDTO.getIdiomas());
                    })
                    .toList();
        }

        // Filtro por disponibilidad
        guias = guias.stream()
                .filter(guia -> isGuiaDisponible(guia.getId(), filtroDTO.getFechaInicio(), filtroDTO.getFechaFin()))
                .toList();

        return guias
                .stream()
                .map(guia -> generarGuiaResponse(guia, new GuiaResponseOptions()))
                .toList();
    }
}
