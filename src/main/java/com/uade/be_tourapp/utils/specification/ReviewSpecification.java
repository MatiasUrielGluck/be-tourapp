package com.uade.be_tourapp.utils.specification;

import com.uade.be_tourapp.entity.Review;
import com.uade.be_tourapp.entity.Viaje;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class ReviewSpecification {
    public ReviewSpecification() {}

    public static Specification<Review> reviewPorGuia(Integer guiaId) {
        return (root, query, builder) -> {
            Join<Review, Viaje> viaje = root.join("viaje");
            return builder.equal(viaje.get("guia").get("id"), guiaId);
        };
    }
}
