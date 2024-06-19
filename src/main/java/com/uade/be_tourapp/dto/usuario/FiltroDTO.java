package com.uade.be_tourapp.dto.usuario;

import com.uade.be_tourapp.enums.TipoServicioEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class FiltroDTO {
    private String nombre;
    private String apellido;
    private String pais;
    private String ciudad;
    private TipoServicioEnum tipoServicio;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private List<String> idiomas;
}
