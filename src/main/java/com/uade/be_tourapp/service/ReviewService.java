package com.uade.be_tourapp.service;

import com.uade.be_tourapp.dto.GenericResponseDTO;
import com.uade.be_tourapp.dto.review.ReviewRequestDTO;
import com.uade.be_tourapp.dto.review.ReviewResponseDTO;
import com.uade.be_tourapp.entity.Review;
import com.uade.be_tourapp.entity.Usuario;
import com.uade.be_tourapp.entity.Viaje;
import com.uade.be_tourapp.exception.BadRequestException;
import com.uade.be_tourapp.repository.ReviewRepository;
import com.uade.be_tourapp.repository.ViajeRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ViajeRepository viajeRepository;
    private final UsuarioService usuarioService;

    public ReviewService(ReviewRepository reviewRepository, ViajeRepository viajeRepository, UsuarioService usuarioService) {
        this.reviewRepository = reviewRepository;
        this.viajeRepository = viajeRepository;
        this.usuarioService = usuarioService;
    }

    public ReviewResponseDTO crearReview(ReviewRequestDTO reviewRequestDTO) {
        Viaje viaje = viajeRepository.findById(reviewRequestDTO.getViajeId()).orElseThrow(()-> new RuntimeException("El viaje no existe"));
        Usuario turista = usuarioService.obtenerAutenticado();
        if (!Objects.equals(viaje.getTurista().getId(), turista.getId())) {
            throw new BadRequestException("No se puede comentar un viaje ajeno.");
        }

        if (reviewRepository.existsByViajeId(reviewRequestDTO.getViajeId())) {
            throw new BadRequestException("Este viaje ya tiene una review.");
        }

        Review review = Review.builder()
                .viaje(viaje)
                .comentario(reviewRequestDTO.getComentario())
                .puntuacion(reviewRequestDTO.getPuntuacion())
                .build();

        Review guardada = reviewRepository.save(review);
        return ReviewResponseDTO.builder()
                .id(guardada.getId())
                .comentario(guardada.getComentario())
                .puntuacion(guardada.getPuntuacion())
                .viajeId(guardada.getViaje().getId())
                .build();
    }

    public GenericResponseDTO eliminarReview(Integer reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new BadRequestException("La review no existe"));
        Usuario turista = usuarioService.obtenerAutenticado();
        if (!Objects.equals(
                review.getViaje().getTurista().getId(),
                turista.getId()
        )) throw new BadRequestException("Acceso denegado");

        reviewRepository.delete(review);

        return GenericResponseDTO.builder()
                .message("Review eliminada")
                .build();
    }
}
