package com.uade.be_tourapp.utils;

import com.uade.be_tourapp.entity.Guia;
import com.uade.be_tourapp.entity.Servicio;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class GuiaSpecification {
    private GuiaSpecification() {}

    /*
        Filtra por nombre
     */
    public static Specification<Guia> nombreLike(String nombre) {
        return (root, query, builder) -> builder.like(root.get("nombre"), "%" + nombre + "%");
    }

    /*
        Filtra por apellido
     */
    public static Specification<Guia> apellidoLike(String apellido) {
        return (root, query, builder) -> builder.like(root.get("apellido"), "%" + apellido + "%");
    }

    /*
        Filtra por pais
     */
    public static Specification<Guia> paisLikeInServicio(String pais) {
        return (root, query, builder) -> {
            Join<Guia, Servicio> guiaServicioJoin = root.join("servicios");
            return builder.like(guiaServicioJoin.get("pais"), "%" + pais + "%");
        };
    }

    /*
        Filtra por ciudad
     */
    public static Specification<Guia> ciudadLikeInServicio(String ciudad) {
        return (root, query, builder) -> {
            Join<Guia, Servicio> guiaServicioJoin = root.join("servicios");
            return builder.like(guiaServicioJoin.get("ciudad"), "%" + ciudad + "%");
        };
    }
}
