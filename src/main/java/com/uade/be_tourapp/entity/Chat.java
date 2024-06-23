package com.uade.be_tourapp.entity;

import com.uade.be_tourapp.dto.chat.ChatDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "chat")
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "turista_id", referencedColumnName = "id")
    private Usuario turista;

    @ManyToOne
    @JoinColumn(name = "guia_id", referencedColumnName = "id")
    private Guia guia;

    @OneToMany(mappedBy = "chat")
    private List<Mensaje> mensajes;

    public ChatDTO toDto() {
        return ChatDTO.builder()
                .id(this.id)
                .turista(this.turista.toDto())
                .guia(this.guia.toDto())
                .build();
    }
}
