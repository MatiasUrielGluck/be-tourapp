package com.uade.be_tourapp.repository;

import com.uade.be_tourapp.entity.Credencial;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredencialRepository extends CrudRepository<Credencial, Long> {
    Boolean existsByNumero(Long numero);
}
