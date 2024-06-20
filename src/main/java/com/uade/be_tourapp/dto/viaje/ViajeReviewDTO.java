package com.uade.be_tourapp.dto.viaje;

import com.uade.be_tourapp.dto.review.ReviewResponseDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ViajeReviewDTO {
    private ViajeResponseDTO viaje;
    private ReviewResponseDTO review;
}
