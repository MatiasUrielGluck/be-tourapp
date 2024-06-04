package com.uade.be_tourapp.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ViajeRequestDTO {
    private Integer turistaId;
    private Integer guiaId;
    private Date fecha;
    private double precio;

}
