package com.uade.be_tourapp.dto.usuario;

import com.uade.be_tourapp.dto.review.ReviewResponseDTO;
import com.uade.be_tourapp.dto.servicio.ServicioResponseDTO;
import com.uade.be_tourapp.entity.Credencial;
import com.uade.be_tourapp.entity.Idioma;
import com.uade.be_tourapp.enums.GenerosEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data @NoArgsConstructor @AllArgsConstructor
public class GuiaResponseDTO {
    private Integer id;
    private String nombre;
    private String apellido;
    private String email;
    private GenerosEnum genero;
    private Integer dni;
    private String foto;
    private Credencial credencial;
    private List<ServicioResponseDTO> servicios;
    private Double puntuacion;
    private List<ReviewResponseDTO> reviews;
    private List<Idioma> idiomas;
}
