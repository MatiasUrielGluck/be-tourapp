package com.uade.be_tourapp.dto.viaje;

import com.uade.be_tourapp.dto.usuario.AccountInfoDTO;
import com.uade.be_tourapp.enums.EstadosViajeEnum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ViajeResponseDTO {
    private Integer id;
    private AccountInfoDTO turista;
    private AccountInfoDTO guia;
    private Integer servicioId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String pais;
    private String ciudad;
    private EstadosViajeEnum estado;
}
