package com.uade.be_tourapp.repository;

import com.uade.be_tourapp.entity.Guia;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuiaRepository extends CrudRepository<Guia, Integer>, JpaSpecificationExecutor<Guia> {
}
