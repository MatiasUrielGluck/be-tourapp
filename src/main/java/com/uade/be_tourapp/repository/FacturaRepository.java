package com.uade.be_tourapp.repository;

import com.uade.be_tourapp.entity.Factura;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturaRepository extends CrudRepository<Factura, Integer> {
    List<Factura> findAllByViajeId(Integer id);
}
