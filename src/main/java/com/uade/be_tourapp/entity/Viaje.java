package com.uade.be_tourapp.entity;

import com.uade.be_tourapp.dto.viaje.ViajeResponseDTO;
import com.uade.be_tourapp.enums.EstadosViajeEnum;
import com.uade.be_tourapp.state.EstadoViaje.EstadoViaje;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "viaje")
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "turista_id", referencedColumnName = "id")
    private Usuario turista;

    @ManyToOne
    @JoinColumn(name = "guia_id", referencedColumnName = "id")
    private Guia guia;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "pais")
    private String pais;

    @Column(name = "ciudad")
    private String ciudad;

    @ManyToOne
    @JoinColumn(name = "servicio_id", referencedColumnName = "id")
    private Servicio servicio;

    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    private EstadosViajeEnum estadoEnum;

    @Transient
    private List<EstadoViaje> estadosDisponibles;

    @Transient
    private EstadoViaje estado;

    public void inicializarEstado(List<EstadoViaje> estadosDisponibles) {
        this.estadosDisponibles = estadosDisponibles;
        cambiarEstado(estadoEnum);
    }

    public void cambiarEstado(EstadosViajeEnum nuevoEstado) {
        this.estadoEnum = nuevoEstado;
        this.estado = this.estadosDisponibles
                .stream()
                .filter(estadoViaje -> estadoViaje.getNombre() == this.estadoEnum)
                .findAny()
                .orElseThrow();
    }

    public void reservar() {
        this.estado.reservar(this);
    }

    public void confirmar() {
        this.estado.confirmar(this);
    }

    public void cancelar(Usuario cancelador) {
        this.estado.cancelar(this, cancelador);
    }

    public void concluir() {
        this.estado.concluir(this);
    }

    public ViajeResponseDTO toDto() {
        return ViajeResponseDTO.builder()
                .id(this.getId())
                .turista(this.turista.toDto())
                .guia(this.guia.toDto())
                .servicioId(this.servicio.getId())
                .fechaInicio(this.getFechaInicio())
                .fechaFin(this.getFechaFin())
                .pais(this.getPais())
                .ciudad(this.getCiudad())
                .estado(this.estadoEnum)
                .build();
    }
}
