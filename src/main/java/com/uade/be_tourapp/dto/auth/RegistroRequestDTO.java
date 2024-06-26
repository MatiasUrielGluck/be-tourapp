package com.uade.be_tourapp.dto.auth;
import com.uade.be_tourapp.enums.AuthStrategiesEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistroRequestDTO {
    @NotNull
    private AuthStrategiesEnum authStrategy;

    @Email @NotNull @NotEmpty
    private String email;

    private String password;
}
