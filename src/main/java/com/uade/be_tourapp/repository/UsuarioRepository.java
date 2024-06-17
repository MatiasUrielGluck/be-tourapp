package com.uade.be_tourapp.repository;

import com.uade.be_tourapp.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {
    @Modifying
    @Query(value = "UPDATE tourapp.usuario u set u.rol = :rol where u.id = :id", nativeQuery = true)
    void updateUserSetRolForId(@Param("rol") String rol,
                                   @Param("id") Integer id);

    Boolean existsByEmail(String email);
    Optional<Usuario> findByDni(Integer dni);
    Optional<Usuario> findByEmail(String email);
}
