package com.uade.be_tourapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Builder
@Data @AllArgsConstructor @NoArgsConstructor
@Table(name = "credencial")
public class Credencial {
    @Id
    @Column(name = "numero")
    private Long numero;

    @Column(name = "vencimiento", nullable = false)
    private LocalDate vencimiento;

    @Column(name = "foto", nullable = false)
    private String foto;
}
