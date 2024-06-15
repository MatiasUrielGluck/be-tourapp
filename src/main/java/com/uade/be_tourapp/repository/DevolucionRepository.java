package com.uade.be_tourapp.repository;

import com.uade.be_tourapp.entity.Devolucion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DevolucionRepository extends CrudRepository<Devolucion, Integer> {
}
