package com.uade.be_tourapp.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class ReviewResponseDTO {
    private Integer id;
    private Integer viajeId;
    private String comentario;
    private Integer puntuacion;
}
