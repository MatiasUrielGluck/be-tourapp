package com.uade.be_tourapp.dto.viaje;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ViajeRequestDTO {
    @NotNull
    private Integer guiaId;

    @NotNull
    private Integer servicioId;

    @NotNull
    private LocalDate fechaInicio;

    @NotNull
    private LocalDate fechaFin;

    @NotNull
    private String pais;

    @NotNull
    private String ciudad;

}
