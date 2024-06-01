package com.uade.be_tourapp.enums;

import lombok.Getter;

@Getter
public enum RolUsuarioEnum {
    TURISTA("TURISTA"),
    GUIA("GUIA");

    private final String label;

    RolUsuarioEnum(String label) {
        this.label = label;
    }
}
