package com.uade.be_tourapp.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ViajeResponseDTO {
    private Integer id;
    private Integer turistaId;
    private Integer guiaId;
    private Date fechaInicio;
    private Date fechaFin;
    private double precio;
}
