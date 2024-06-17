package com.uade.be_tourapp.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class GuiaResponseOptions {
    private Boolean incluirCredencial = false;
    private Boolean incluirReviews = false;
}
