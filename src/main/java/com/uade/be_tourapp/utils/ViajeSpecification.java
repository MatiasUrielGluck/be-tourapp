package com.uade.be_tourapp.utils;

import com.uade.be_tourapp.entity.Viaje;
import com.uade.be_tourapp.enums.EstadosViajeEnum;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ViajeSpecification {
    private ViajeSpecification() {}

    /*
        Filtra aquellos guías disponibles.
        Un guía está disponible cuando ningún viaje se superpone con las fechas.
     */
    public static Specification<Viaje> guiaDisponible(Integer guiaId, LocalDate fechaInicio, LocalDate fechaFin) {
        return (root, query, builder) -> {
            Subquery<Integer> subquery = query.subquery(Integer.class);
            Root<Viaje> subqueryRoot = subquery.from(Viaje.class);
            subquery.select(subqueryRoot.get("id"));

            List<EstadosViajeEnum> estadosValidos = Arrays.asList(EstadosViajeEnum.RESERVADO, EstadosViajeEnum.CONFIRMADO);

            Predicate guidePredicate = builder.equal(subqueryRoot.get("guia").get("id"), guiaId);
            Predicate viajeStatePredicate = subqueryRoot.get("estadoEnum").in(estadosValidos);
            Predicate startDatePredicate = builder.lessThanOrEqualTo(subqueryRoot.get("fechaInicio"), fechaFin);
            Predicate endDatePredicate = builder.greaterThanOrEqualTo(subqueryRoot.get("fechaFin"), fechaInicio);

            subquery.where(builder.and(guidePredicate, viajeStatePredicate, startDatePredicate, endDatePredicate));

            return builder.exists(subquery);
        };
    }
}
