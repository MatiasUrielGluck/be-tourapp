package com.uade.be_tourapp.dto.viaje;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ViajeRequestDTO {
    @NotNull
    private Integer guiaId;

    @NotNull
    private Integer servicioId;

    @NotNull
    private Date fechaInicio;

    @NotNull
    private Date fechaFin;

    @NotNull
    private String pais;

    @NotNull
    private String ciudad;

}
