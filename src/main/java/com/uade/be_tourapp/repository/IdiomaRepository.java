package com.uade.be_tourapp.repository;

import com.uade.be_tourapp.entity.Idioma;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdiomaRepository extends CrudRepository<Idioma, Integer> {
    Optional<Idioma> findByNombre(String nombre);
}
