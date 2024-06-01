package com.uade.be_tourapp.repository;

import com.uade.be_tourapp.entity.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {

    Boolean existsByEmail(String email);
    Optional<Usuario> findByEmail(String email);
}
