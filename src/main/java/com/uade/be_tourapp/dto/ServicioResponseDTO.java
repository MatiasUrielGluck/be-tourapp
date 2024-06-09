package com.uade.be_tourapp.dto;

import com.uade.be_tourapp.enums.TipoServicioEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServicioResponseDTO {
    private Integer id;
    private TipoServicioEnum tipo;
    private Double precio;
    private String pais;
    private String ciudad;
}
