package com.uade.be_tourapp.dto.documento;

import com.uade.be_tourapp.entity.Viaje;
import com.uade.be_tourapp.enums.DocumentoEnum;
import lombok.*;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class DocumentoDTO {
    private Integer id;
    private Double importe;
    private Double comision;
    private DocumentoEnum tipo;
    private Viaje viaje;
    private Boolean pagada;
}
