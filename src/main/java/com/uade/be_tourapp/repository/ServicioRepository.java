package com.uade.be_tourapp.repository;

import com.uade.be_tourapp.entity.Servicio;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioRepository extends CrudRepository<Servicio, Integer> {
}
