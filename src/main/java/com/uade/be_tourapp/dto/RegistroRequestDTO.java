package com.uade.be_tourapp.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistroRequestDTO {
    @Email @NotNull
    private String email;

    @NotNull @NotEmpty
    private String password;

    @NotNull @NotEmpty
    private String nombre;

    @NotNull @NotEmpty
    private String apellido;

    @NotNull @NotEmpty
    private String sexo;

    @NotNull
    private Integer dni;

    @NotNull @NotEmpty
    private String numTelefono;

    private String foto;
}
