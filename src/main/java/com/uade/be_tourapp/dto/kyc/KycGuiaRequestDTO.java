package com.uade.be_tourapp.dto.kyc;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class KycGuiaRequestDTO {
    @NotNull
    private Long numero;

    @NotNull
    private LocalDate vencimiento;

    @NotNull @NotEmpty
    private String foto;
}
