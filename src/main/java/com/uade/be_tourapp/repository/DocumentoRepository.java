package com.uade.be_tourapp.repository;

import com.uade.be_tourapp.entity.Documento;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentoRepository extends CrudRepository<Documento, Integer> {
}
