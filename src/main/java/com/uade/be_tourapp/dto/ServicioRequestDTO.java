package com.uade.be_tourapp.dto;

import com.uade.be_tourapp.enums.TipoServicioEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ServicioRequestDTO {
    @NotNull
    private TipoServicioEnum tipo;

    @NotNull
    private Double precio;

    @NotNull
    private String pais;

    @NotNull
    private String ciudad;
}
