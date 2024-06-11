package com.uade.be_tourapp.entity;

import com.uade.be_tourapp.enums.EstadosViajeEnum;
import com.uade.be_tourapp.state.EstadoViaje.EstadoViaje;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    private EstadosViajeEnum nombreEstado;

    @Transient
    private EstadoViaje estado;

    @PostLoad
    public void postLoad() {
        this.estado = getNombreEstado().getEstado();
    }

    public void cambiarEstado(EstadoViaje estado) {
        this.estado = estado;
        this.nombreEstado = estado.getNombre();
    }

    public void confirmar() {
        this.estado.confirmar(this);
    }

    public void cancelar() {
        this.estado.cancelar(this);
    }

    public void concluir() {
        this.estado.concluir(this);
    }
}
