package com.uade.be_tourapp.dto;

import com.uade.be_tourapp.enums.DocumentoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class FacturaDTO {
    private Integer id;
    private Integer viajeId;
    private Double total;
    private Double precio;
    private Double comision;
    private DocumentoEnum motivo;
    private Boolean pagada;
}
