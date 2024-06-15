package com.uade.be_tourapp.dto.kyc;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class KycGuiaRequestDTO {
    @NotNull
    private Long numero;

    @NotNull
    private Date vencimiento;

    @NotNull @NotEmpty
    private String foto;
}
