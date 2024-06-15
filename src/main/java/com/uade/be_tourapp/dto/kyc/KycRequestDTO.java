package com.uade.be_tourapp.dto.kyc;

import com.uade.be_tourapp.enums.GenerosEnum;
import com.uade.be_tourapp.enums.RolUsuarioEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KycRequestDTO {
    @NotNull
    private RolUsuarioEnum rol;

    @NotNull @NotEmpty
    private String nombre;

    @NotNull @NotEmpty
    private String apellido;

    @NotNull
    private GenerosEnum genero;

    @NotNull
    private Integer dni;

    @NotNull @NotEmpty
    private String numTelefono;

    @NotNull @NotEmpty
    private String foto;
}
