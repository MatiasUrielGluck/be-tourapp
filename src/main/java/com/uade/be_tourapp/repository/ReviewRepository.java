package com.uade.be_tourapp.repository;

import com.uade.be_tourapp.entity.Review;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Integer>, JpaSpecificationExecutor<Review> {
    Boolean existsByViajeId(Integer viajeId);
    Optional<Review> findByViajeId(Integer viajeId);
}
