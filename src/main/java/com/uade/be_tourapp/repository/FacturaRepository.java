package com.uade.be_tourapp.repository;

import com.uade.be_tourapp.entity.Factura;
import com.uade.be_tourapp.enums.DocumentoEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacturaRepository extends CrudRepository<Factura, Integer> {
    List<Factura> findAllByViajeId(Integer id);

    Optional<Factura> findByViajeIdAndMotivo(Integer id, DocumentoEnum motivo);
}
