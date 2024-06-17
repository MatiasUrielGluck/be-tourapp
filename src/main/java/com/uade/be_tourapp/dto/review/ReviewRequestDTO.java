package com.uade.be_tourapp.dto.review;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class ReviewRequestDTO {
    @NotNull
    private Integer viajeId;

    @NotNull @NotEmpty
    private String comentario;

    @NotNull
    private Double puntuacion;
}
