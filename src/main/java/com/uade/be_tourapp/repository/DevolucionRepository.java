package com.uade.be_tourapp.repository;

import com.uade.be_tourapp.entity.Devolucion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DevolucionRepository extends CrudRepository<Devolucion, Integer> {
    List<Devolucion> findAllByViajeId(Integer id);
}
