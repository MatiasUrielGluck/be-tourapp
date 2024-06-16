package com.uade.be_tourapp.dto.usuario;

import com.uade.be_tourapp.enums.GenerosEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountInfoDTO {
    private String email;
    private String nombre;
    private String apellido;
    private GenerosEnum genero;
    private Integer dni;
    private String numTelefono;
    private String foto;
    private Boolean kycCompleted;
}
