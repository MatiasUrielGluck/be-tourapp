package com.uade.be_tourapp.entity;

import com.uade.be_tourapp.enums.trofeo.TrofeoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trofeo")
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Trofeo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TrofeoEnum tipo;
}
