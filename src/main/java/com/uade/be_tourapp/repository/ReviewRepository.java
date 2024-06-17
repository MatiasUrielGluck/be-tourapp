package com.uade.be_tourapp.repository;

import com.uade.be_tourapp.entity.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Integer> {
    Boolean existsByViajeId(Integer viajeId);
}
