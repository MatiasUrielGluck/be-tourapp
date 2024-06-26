package com.uade.be_tourapp.repository;

import com.uade.be_tourapp.entity.Notificacion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepository extends CrudRepository<Notificacion, Long> {
    List<Notificacion> findAllByUsuarioId(Integer id);
}
