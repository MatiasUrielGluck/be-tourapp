package com.uade.be_tourapp.entity;

import com.uade.be_tourapp.enums.EstadosViajeEnum;
import com.uade.be_tourapp.state.EstadoViaje.EstadoViaje;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
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
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_fin")
    private Date fechaFin;

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

    public void confirmar() {
        this.estado.confirmar(this);
    }

    public void cancelar(Usuario cancelador) {
        this.estado.cancelar(this, cancelador);
    }

    public void concluir() {
        this.estado.concluir(this);
    }
}
