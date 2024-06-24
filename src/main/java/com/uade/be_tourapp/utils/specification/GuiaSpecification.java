package com.uade.be_tourapp.utils.specification;

import com.uade.be_tourapp.entity.Guia;
import com.uade.be_tourapp.entity.Servicio;
import com.uade.be_tourapp.enums.TipoServicioEnum;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
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

    /*
        Filtra por tipo de servicio
     */
    public static Specification<Guia> tipoServicioInServicio(TipoServicioEnum tipo, String ciudad) {
        return (root, query, builder) -> {
            Join<Guia, Servicio> guiaServicioJoin = root.join("servicios", JoinType.INNER);

            // Adding conditions
            Predicate tipoPredicate = builder.equal(guiaServicioJoin.get("tipo"), tipo);
            Predicate ciudadPredicate = builder.equal(guiaServicioJoin.get("ciudad"), ciudad);

            // Combining predicates

            return builder.and(tipoPredicate, ciudadPredicate);
        };
    }
}
