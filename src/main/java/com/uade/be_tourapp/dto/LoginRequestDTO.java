package com.uade.be_tourapp.dto;
import com.uade.be_tourapp.enums.AuthStrategiesEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequestDTO {
    @NotNull
    private AuthStrategiesEnum authStrategy;

    @NotNull @Email
    private String email;
    private String password;
}
