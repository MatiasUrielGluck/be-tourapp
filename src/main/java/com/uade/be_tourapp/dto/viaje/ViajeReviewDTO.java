package com.uade.be_tourapp.dto.viaje;

import com.uade.be_tourapp.dto.review.ReviewResponseDTO;
import com.uade.be_tourapp.dto.servicio.ServicioResponseDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ViajeReviewDTO {
    private ViajeResponseDTO viaje;
    private ReviewResponseDTO review;
    private ServicioResponseDTO servicio;
}
