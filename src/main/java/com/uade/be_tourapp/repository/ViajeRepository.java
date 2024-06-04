package com.uade.be_tourapp.repository;

import com.uade.be_tourapp.entity.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViajeRepository extends CrudRepository<Viaje, Integer> {
}
