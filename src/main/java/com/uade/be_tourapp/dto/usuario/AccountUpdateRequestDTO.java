package com.uade.be_tourapp.dto.usuario;

import com.uade.be_tourapp.enums.GenerosEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountUpdateRequestDTO {
    private String nombre;
    private String apellido;
    private String foto;
    private String numTelefono;
    private GenerosEnum genero;
}
