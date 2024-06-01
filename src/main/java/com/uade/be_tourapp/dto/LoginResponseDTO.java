package com.uade.be_tourapp.dto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    private String token;
    private Long expiresIn;
}
