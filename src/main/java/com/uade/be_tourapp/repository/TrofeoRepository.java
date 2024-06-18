package com.uade.be_tourapp.repository;

import com.uade.be_tourapp.entity.Trofeo;
import com.uade.be_tourapp.enums.trofeo.TrofeoEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrofeoRepository extends CrudRepository<Trofeo, Long> {
    Boolean existsByUsuarioIdAndTipo(Integer usuarioId, TrofeoEnum tipo);
}
