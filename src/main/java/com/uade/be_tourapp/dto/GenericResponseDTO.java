package com.uade.be_tourapp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenericResponseDTO {
    private String message;
    private Object extra;
}
